package org.todo.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{
    // Server Exception
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "[서버]Internal Server Error"),

    // Member Exception
    LOGIN_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "[로그인] 존재하지 않는 이메일"),
    PASSWORD_NOT_CORRECT(HttpStatus.BAD_REQUEST, 400, "[로그인] 비밀번호 불일치"),
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, 400, "[회원가입] 이메일 중복"),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, 400, "[회원가입] 닉네임 중복"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "[서비스] 존재하지 않는 맴버"),

    // Todos Exception
    TODO_PERMISSION_DENIED(HttpStatus.FORBIDDEN, 403, "[투두] 수정/삭제 권한 없음"),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "[투두] 투두 조회 실패"),


    // Posts Exception
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "[포스팅] 포스팅 조회 실패"),
    POST_TODO_NOT_DONE(HttpStatus.BAD_REQUEST, 404, "[포스팅] 포스트 생성 실패 - 투두 미완료"),

    // jwt Exception
    JWT_NOT_VALID(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 유효하지 않은 Jwt"),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, 419, "[Jwt] 만료된 토큰입니다"),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 잘못된 토큰 형식입니다"),
    JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 유효하지 않은 서명입니다"),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 지원하지 않는 토큰입니다"),

    // Valid Exception
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, 400, "[요청] RequestBody 유효성 검사 실패");


    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
