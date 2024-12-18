package org.todo.domain.member.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.todo.domain.member.dto.req.LoginRequestDto;
import org.todo.domain.member.dto.req.SignupRequestDto;
import org.todo.domain.member.dto.res.LoginResponseDto;
import org.todo.domain.member.dto.res.SignupResponseDto;
import org.todo.domain.member.entity.Member;
import org.todo.domain.member.repository.MemberRepository;
import org.todo.global.error.CustomErrorCode;
import org.todo.global.exception.RestApiException;
import org.todo.global.security.jwt.JwtTokenProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto req){

        checkEmailDuplicated(req.getEmail());
        checkNicknameDuplicated(req.getNickname());

        Member member = new Member(req, passwordEncoder.encode(req.getPassword()));

        memberRepository.save(member);

        return SignupResponseDto.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .build();
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto req){
        Member member = memberRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.LOGIN_MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(req.getPassword(), member.getEncodedPW())) {
            log.info("Username = {}, Password = {} ", member.getUsername(), member.getPassword());
            throw new RestApiException(CustomErrorCode.PASSWORD_NOT_CORRECT);
        }

        return LoginResponseDto.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(member.getUsername()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(member.getUsername()))
                .build();
    }

    @Transactional
    public Boolean checkEmailDuplicated(String email){
        if(memberRepository.existsByEmail(email))
            throw new RestApiException(CustomErrorCode.EMAIL_DUPLICATED);

        return true;
    }

    @Transactional
    public Boolean checkNicknameDuplicated(String nickname){
        if(memberRepository.existsByNickname(nickname))
            throw new RestApiException(CustomErrorCode.NICKNAME_DUPLICATED);

        return true;
    }

    @Transactional
    public String updatePersonalColor(Member member, String colorCode){
        Member newMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.LOGIN_MEMBER_NOT_FOUND));

        newMember.updatePersonalColor(colorCode);

        return colorCode;
    }
}
