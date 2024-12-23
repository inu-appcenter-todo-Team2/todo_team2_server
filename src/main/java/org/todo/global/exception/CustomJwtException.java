package org.todo.global.exception;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import org.todo.global.error.CustomErrorCode;
import org.todo.global.error.ErrorCode;

@Getter
public class CustomJwtException extends JwtException {

    private final ErrorCode errorCode;

    public CustomJwtException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomJwtException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
