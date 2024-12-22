package org.todo.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.todo.domain.global.ResponseDto;
import org.todo.domain.member.entity.Member;
import org.todo.domain.post.dto.req.UploadPostRequestDto;
import org.todo.domain.post.dto.res.ExplorePostResponseDto;
import org.todo.domain.post.dto.res.UploadPostResponseDto;
import org.todo.domain.post.service.PostService;
import org.todo.domain.s3.dto.res.PresignedUrlResponseDto;
import org.todo.domain.s3.service.S3Service;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    @GetMapping("/presignedUrl/{todoId}")
    public ResponseEntity<ResponseDto<PresignedUrlResponseDto>> getPresignedUrl(@AuthenticationPrincipal Member member,
                                                                                @PathVariable Long todoId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(s3Service.generateUploadPreSignedUrl(member, todoId), "S3 PresignedUrl 발급 완료"));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<UploadPostResponseDto>> createPost(@AuthenticationPrincipal Member member,
                                                                         @Valid @RequestBody UploadPostRequestDto req){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(postService.createPost(member, req), "포스팅 업로드 완료"));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto<Boolean>> deletePost(@AuthenticationPrincipal Member member,
                                                           @PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(postService.deletePost(member, postId), "포스팅 삭제 완료"));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<ExplorePostResponseDto>> getAllPost(@RequestParam(required = false) Long cursor){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(postService.getAllPost(cursor), "포스팅 조회 완료"));
    }
}