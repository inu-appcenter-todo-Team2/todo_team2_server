package org.todo.domain.post.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "단일 포스트 조회 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    @Schema(description = "포스팅 아이디",  example = "1")
    private Long postId;

    @Schema(description = "사용자 이름", example = "한준서")
    private String username;

    @Schema(description = "투두 제목", example = "방어회 먹으러 가기")
    private String todoTitle;

    @Schema(description = "이미지 url", example = "https://appcenterbucket.s3.ap-northeast-2.amazonaws.com/todo/")
    private String presignedUrl;
}