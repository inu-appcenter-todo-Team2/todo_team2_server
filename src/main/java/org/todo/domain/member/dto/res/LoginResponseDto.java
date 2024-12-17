package org.todo.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    @Schema(description = "엑세스 토큰", example = "adslfjkhqejd.adfasdfqwe.adsfad")
    private String accessToken;

    @Schema(description = "리프레시 토큰", example = "adfew.efadfew.qefadf")
    private String refreshToken;
}
