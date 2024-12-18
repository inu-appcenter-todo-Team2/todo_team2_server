package org.todo.domain.todo.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "투두 생성 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthTodoResponseDto {

    @Schema(description = "투두 날짜", example = "2024-11-01")
    private LocalDate date;

    @Schema(description = "완료하지 않은 투두 개수", example = "3")
    private Integer notDoneTodoCount;

    @Schema(description = "완료한 투두 존재 여부", example = "true")
    private Boolean hasDoneTodo;
}