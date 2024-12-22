package org.todo.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@Tag(name = "맴버 로그인/회원가입", description = "맴버 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class MemberController {

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