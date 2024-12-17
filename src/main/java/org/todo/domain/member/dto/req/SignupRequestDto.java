package org.todo.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "회원가입 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequestDto {

    @Schema(description = "유저 이메일", example = "ahh0520@naver.com")
    @NotBlank(message = "이메일은 공백이 될 수 없습니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    private String email;

    @Schema(description = "유저 비밀번호", example = "test1234")
    @NotBlank(message = "비밀번호는 공백이 될 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{1,15}$")
    private String password;

    @Schema(description = "유저 이름", example = "한준서")
    @NotBlank(message = "이름은 공백이 될 수 없습니다.")
    private String username;

    @Schema(description = "유저 닉네임", example = "준소이")
    @NotBlank(message = "닉네임은 공백이 될 수 없습니다.")
    private String nickname;

    @Schema(description = "유저 생일", example = "2001-05-20")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Schema(description = "유저 성별", example = "남성/여성")
    @Pattern(regexp = "^(MALE|FEMALE)$")
    private String gender;
}
