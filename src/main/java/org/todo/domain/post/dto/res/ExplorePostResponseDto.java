package org.todo.domain.post.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "전체 포스트 조회 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExplorePostResponseDto {

    @Schema(description = "포스팅 데이터")
    private List<PostResponseDto> posts;

    @Schema(description = "cursor 값", example = "13")
    private Long cursor;
}
