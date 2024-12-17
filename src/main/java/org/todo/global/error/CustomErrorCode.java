package org.todo.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{
    // common error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),
    // todos error
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Todo not found"),
    // user error
    LOGIN_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "[로그인] 존재하지 않는 이메일"),
    PASSWORD_NOT_CORRECT(HttpStatus.BAD_REQUEST, 400, "[로그인] 비밀번호 불일치"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, 403, "Permission denied"),

    // jwt
    JWT_NOT_VALID(HttpStatus.UNAUTHORIZED, 401, "Jwt is not Valid"),
    // validation
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, 400, "Validation Failed"),


    // member
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, 400, "[회원가입] 이메일 중복"),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, 400, "[회원가입] 닉네임 중복");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
