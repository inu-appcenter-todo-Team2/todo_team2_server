package org.todo.domain.todo.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.todo.domain.todo.entity.PriorityType;

@Schema(description = "투두 일별 조회 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayTodoResponseDto {

    @Schema(description = "투두 아이디", example = "1")
    private Long todoId;

    @Schema(description = "제목", example = "방어회 먹으러 가기")
    private String title;

    @Schema(description = "설명", example = "타임스페이스 도시어부")
    private String description;

    @Schema(description = "완료 여부", example = "true")
    private Boolean done;

    @Schema(description = "우선순위", example = "HIGH")
    private PriorityType priority;
}