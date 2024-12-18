package org.todo.domain.todo.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.todo.domain.todo.entity.PriorityType;

@Schema(description = "투두 수정 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoUpdateRequestDto {

    @Schema(description = "제목", example = "방어회 먹으러 가기")
    @NotBlank(message = "제목은 공백이 될 수 없습니다.")
    @Size(max = 15, message = "제목은 15자를 초과할 수 없습니다.")
    private String title;

    @Schema(description = "설명", example = "타임스페이스 도시어부")
    @Size(max = 20, message = "제목은 15자를 초과할 수 없습니다.")
    private String description;

    @Schema(description = "우선순위", example = "HIGH")
    @NotNull(message = "우선순위는 필수값입니다")
    private PriorityType priority;
}
