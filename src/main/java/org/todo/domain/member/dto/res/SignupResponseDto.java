package org.todo.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 Response")
@Getter
@NoArgsConstructor
public class SignupResponseDto {

    @Schema(description = "유저 이메일", example = "ahh0520@naver.com")
    private String email;

    @Schema(description = "유저 이름", example = "한준서")
    private String username;

    @Schema(description = "회원가입 완료 메시지", example = "회원가입이 완료되었습니다.")
    private final String msg = "회원가입이 완료되었습니다.";

    @Builder
    private SignupResponseDto(String email, String username){
        this.email = email;
        this.username = username;
    }
}