package org.todo.domain.todo.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "투두 수정 Response")
@Getter
@NoArgsConstructor
public class TodoUpdateResponseDto {

    @Schema(description = "유저 이메일", example = "ahh0520@naver.com")
    private String email;

    @Schema(description = "투두 타이틀", example = "방어회 먹으러 가기")
    private String title;

    @Builder
    private TodoUpdateResponseDto(String email, String title){
        this.email = email;
        this.title = title;
    }
}