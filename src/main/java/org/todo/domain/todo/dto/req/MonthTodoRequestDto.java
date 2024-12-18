package org.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "투두 월별 조회 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MonthTodoRequestDto {

    @Schema(description = "조회할 연월", example = "yyyy-MM")
    @Pattern(regexp = "^\\d{4}-(?:0[1-9]|1[0-2])$", message = "날짜 형식은 yyyy-MM 이어야 합니다")
    private String date;
}