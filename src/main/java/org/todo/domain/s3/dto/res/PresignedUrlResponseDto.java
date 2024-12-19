package org.todo.domain.s3.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "S3 PresignedUrl 발급 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PresignedUrlResponseDto {

    @Schema(description = "S3 PresignedUrl", example = "https://appcenterbucket.s3.ap-northeast-2.amazonaws.com/todo/")
    private String presignedUrl;

    @Schema(description = "이미지 객체 키값", example = "asdfadfads")
    private String objectKey;
}
