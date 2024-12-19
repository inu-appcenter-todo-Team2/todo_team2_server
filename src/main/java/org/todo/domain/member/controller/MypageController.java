package org.todo.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MypageController {
    /**
     * FEAT 0) 프로필 조회(개인 정보, 본인 포스트)
     * FEAT 1) 퍼스널 컬러 수정
     * FEAT 2) 상태메시지 수정
     */
}
