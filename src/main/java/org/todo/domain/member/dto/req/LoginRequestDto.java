package org.todo.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDto {

    @Schema(description = "유저 이메일", example = "ahh0520@naver.com")
    @NotBlank(message = "이메일은 공백이 될 수 없습니다.")
    private String email;

    @Schema(description = "유저 비밀번호", example = "test1234")
    @NotBlank(message = "비밀번호는 공백이 될 수 없습니다.")
    private String password;
}
