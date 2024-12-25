package org.todo.global.security.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.todo.domain.member.dto.req.RefreshTokenRequestDto;
import org.todo.domain.member.dto.res.LoginResponseDto;
import org.todo.global.error.CustomErrorCode;
import org.todo.global.exception.CustomJwtException;
import org.todo.global.security.jwt.entity.RefreshToken;
import org.todo.global.security.jwt.repository.RefreshTokenRepository;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRED_TIME = 2 * 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_EXPIRED_TIME = 3 * 24 * 60 * 60 * 1000L;

    private final Key key;

    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenProvider(@Value("${jwt.secret}")String jwtSecret, UserDetailsService userDetailsService, RefreshTokenRepository refreshTokenRepository){
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponseDto getNewToken(RefreshTokenRequestDto req){

        String username = getUsernameFromExpiredJwt(req.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByUsername(username)
                .orElseThrow(() -> new CustomJwtException(CustomErrorCode.JWT_NOT_FOUND));

        if(!Objects.equals(req.getRefreshToken(), refreshToken.getRefreshToken()))
            throw new CustomJwtException(CustomErrorCode.JWT_NOT_MATCH);

        if(!validateRefreshToken(refreshToken.getRefreshToken()))
            throw new CustomJwtException(CustomErrorCode.JWT_REFRESH_TOKEN_EXPIRED);

        return LoginResponseDto.builder()
                .accessToken(generateAccessToken(username))
                .refreshToken(generateRefreshToken(username))
                .build();
    }

    public String generateAccessToken(String username){
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username){
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        refreshTokenRepository.save(RefreshToken.builder()
                .username(username)
                .refreshToken(refreshToken)
                .build()
        );

        return refreshToken;
    }

    public String getJwtFromRequest(HttpServletRequest request){
        log.info("Uri = {}", request.getRequestURI());
        log.info("PathInfo = {}", request.getPathInfo());
        log.info("method = {}", request.getMethod());

        return request
                .getHeader("Authorization")
                .substring(7);
    }

    public boolean validateAccessToken(String token) {
        if(token == null) {
            throw new JwtException("Jwt AccessToken not found");
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new CustomJwtException(CustomErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            throw new CustomJwtException(CustomErrorCode.JWT_MALFORMED);
        } catch (SignatureException | SecurityException e) {
            throw new CustomJwtException(CustomErrorCode.JWT_SIGNATURE);
        } catch (UnsupportedJwtException e) {
            throw new CustomJwtException(CustomErrorCode.JWT_UNSUPPORTED);
        }
    }

    public boolean validateRefreshToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public Authentication getAuthenticationJwt(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsernameFromJwt(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsernameFromJwt(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getUsernameFromExpiredJwt(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (ExpiredJwtException e){
            return e.getClaims().getSubject();
        }
    }
}
