package org.todo.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "토큰 재발급 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenRequestDto {

    @Schema(description = "만료된 엑세스 토큰", example = "asdfqwefasd.asdfqwefasdf.qewfasdfqwe")
    @NotBlank(message = "엑세스 토큰은 공백일 수 없습니다.")
    private String accessToken;

    @Schema(description = "리프레시 토큰", example = "asdfqwefasd.asdfqwefasdf.qewfasdfqwe")
    @NotBlank(message = "리프레시 토큰은 공백일 수 없습니다.")
    private String refreshToken;
}
