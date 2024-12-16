package org.todo.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.todo.global.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
}
