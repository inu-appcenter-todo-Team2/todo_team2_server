package org.todo.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.todo.domain.global.ResponseDto;
import org.todo.domain.member.dto.req.UpdateStatusMessageRequestDto;
import org.todo.domain.member.dto.res.UserInfoResponseDto;
import org.todo.domain.member.entity.Member;
import org.todo.domain.member.service.MypageService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> getUserInfo(@AuthenticationPrincipal Member member){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(mypageService.getUserInfo(member), "유저 정보 조회 완료"));
    }

    @PutMapping("/statusMessage")
    public ResponseEntity<ResponseDto<Boolean>> updateStatusMessage(@AuthenticationPrincipal Member member,
                                                                    @Valid @RequestBody UpdateStatusMessageRequestDto req){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(mypageService.updateStatusMessage(member, req), "상태 메시지 수정 완료"));
    }
}
