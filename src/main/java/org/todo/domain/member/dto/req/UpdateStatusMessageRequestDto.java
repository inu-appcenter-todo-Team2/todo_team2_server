package org.todo.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStatusMessageRequestDto {

    @Schema(description = "유저 상태메시지", example = "BE Developer")
    @NotBlank(message = "상태메시지는 공백이 될 수 없습니다.")
    private String statusMessage;
}
