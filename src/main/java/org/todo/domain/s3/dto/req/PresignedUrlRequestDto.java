package org.todo.domain.s3.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "S3 PresignedUrl 발급 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PresignedUrlRequestDto {

    @Schema(description = "투두 아이디", example = "1")
    @NotNull(message = "투두 아이디는 공백이 될 수 없습니다.")
    private Long todoId;
}
