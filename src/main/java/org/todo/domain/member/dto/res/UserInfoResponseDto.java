package org.todo.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.todo.domain.post.dto.res.PostResponseDto;

import java.util.List;

@Schema(description = "유저 정보 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {

    @Schema(description = "유저 닉네임", example = "준소이")
    private String nickname;

    @Schema(description = "상태 메시지", example = "BE Developer")
    private String statusMessage;

    @Schema(description = "포스팅")
    private List<PostResponseDto> posts;
}
