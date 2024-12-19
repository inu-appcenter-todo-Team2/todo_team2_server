package org.todo.domain.post.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "포스팅 생성 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UploadPostRequestDto {
    @Schema(description = "투두 아이디", example = "1")
    @NotNull(message = "투두 아이디는 공백이 될 수 없습니다.")
    private Long todoId;

    @Schema(description = "이미지 객체 키값", example = "queyfgkahdjsbf")
    @NotBlank(message = "이미지 객체 키값은 공백이 될 수 없습니다.")
    private String objectKey;
}
