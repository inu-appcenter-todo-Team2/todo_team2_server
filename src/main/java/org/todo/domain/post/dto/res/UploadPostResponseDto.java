package org.todo.domain.post.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "포스트 생성 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadPostResponseDto {

    @Schema(description = "투두 제목", example = "방어회 먹으러 가기")
    private String todoTitle;

    @Schema(description = "포스팅 아이디", example = "1")
    private Long postId;
}
