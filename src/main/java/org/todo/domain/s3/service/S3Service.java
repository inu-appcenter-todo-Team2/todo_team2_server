package org.todo.domain.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.todo.domain.image.entity.Image;
import org.todo.domain.image.repository.ImageRepository;
import org.todo.domain.member.entity.Member;
import org.todo.domain.s3.dto.res.PresignedUrlResponseDto;
import org.todo.domain.todo.entity.Todo;
import org.todo.domain.todo.repository.TodoRepository;
import org.todo.global.error.CustomErrorCode;
import org.todo.global.exception.RestApiException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class S3Service {
    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;
    private final TodoRepository todoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String getImageUrl(String objectKey) {
        return amazonS3.generatePresignedUrl(bucketName, objectKey, getExpiredDate(), HttpMethod.GET).toString();
    }

    @Transactional
    public PresignedUrlResponseDto generateUploadPreSignedUrl(Member member, Long todoId) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.TODO_NOT_FOUND));

        if(!Objects.equals(todo.getMember().getId(), member.getId()))
            throw new RestApiException(CustomErrorCode.TODO_PERMISSION_DENIED);

        String objectKey = createObjectKey(todoId);

        String presignedUrl = amazonS3.generatePresignedUrl(bucketName,
                objectKey,
                getExpiredDate(),
                HttpMethod.PUT).toString();

        return PresignedUrlResponseDto.builder()
                .presignedUrl(presignedUrl)
                .objectKey(objectKey)
                .build();
    }

    private String createObjectKey(Long todoId) {
        return "todo/" + todoId.toString() + "-" + UUID.randomUUID();
    }

    private Date getExpiredDate() {
        return Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .plusHours(1)
                .toInstant());
    }
}