package org.todo.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todo.domain.global.ResponseDto;
import org.todo.domain.member.dto.req.LoginRequestDto;
import org.todo.domain.member.dto.req.SignupRequestDto;
import org.todo.domain.member.dto.res.LoginResponseDto;
import org.todo.domain.member.dto.res.SignupResponseDto;
import org.todo.domain.member.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class MemberController {
    /**
     * FEAT 1) 회원가입 ✅
     * FEAT 1-1) 이메일 중복체크 ✅
     * FEAT 1-2) 닉네임 중복체크 ✅
     * FEAT 1-3) 회원가입 ✅
     *
     * FEAT 2) 로그인 ✅
     * FEAT 3) 로그아웃
     */

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto req){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(memberService.signup(req), "회원가입 완료"));
    }

    @GetMapping("/signup/email/{email}")
    public ResponseEntity<ResponseDto<Boolean>> checkEmailDuplicated(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(memberService.checkEmailDuplicated(email), "사용가능한 이메일"));
    }

    @GetMapping("/signup/nickname/{nickname}")
    public ResponseEntity<ResponseDto<Boolean>> checkNicknameDuplicated(@PathVariable String nickname){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(memberService.checkNicknameDuplicated(nickname), "사용가능한 닉네임"));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto req){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(memberService.login(req), "로그인 성공"));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<ResponseDto<LogoutResponseDto>> logout(@RequestBody LogoutRequestDto logoutRequestDto){
//
//    }
}