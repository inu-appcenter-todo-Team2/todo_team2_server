package org.todo.domain.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.todo.domain.global.ResponseDto;
import org.todo.domain.todo.dto.req.MonthTodoRequestDto;
import org.todo.domain.todo.dto.res.DayTodoResponseDto;
import org.todo.domain.todo.dto.res.MonthTodoResponseDto;
import org.todo.domain.member.entity.Member;
import org.todo.domain.todo.dto.req.TodoCreateRequestDto;
import org.todo.domain.todo.dto.req.TodoUpdateRequestDto;
import org.todo.domain.todo.dto.res.TodoCreateResponseDto;
import org.todo.domain.todo.dto.res.TodoUpdateResponseDto;
import org.todo.domain.todo.service.TodoService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {
    /**
     * FEAT 1) 투두 추가하기 ✅
     * FEAT 2) 투두 수정하기 ✅
     * FEAT 3) 투두 삭제하기 ✅
     * FEAT 4) 투두 완료/미완료 처리 ✅
     * FEAT 5) 투두 공유하기
     * FEAT 6) 투두 색상 선택하기 ✅
     * FEAT 7) 투두 날짜별 조회하기 ✅
     * FEAT 8) 투두 월별 조회하기 ✅
     */

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<ResponseDto<TodoCreateResponseDto>> createTodo(@AuthenticationPrincipal Member member,
                                                                         @Valid @RequestBody TodoCreateRequestDto req){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(todoService.createTodo(member, req), "투두 생성 완료"));
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<ResponseDto<TodoUpdateResponseDto>> updateTodo(@AuthenticationPrincipal Member member,
                                                                         @PathVariable Long todoId,
                                                                         @Valid @RequestBody TodoUpdateRequestDto req){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.updateTodo(member, todoId,req), "투두 수정 완료"));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<ResponseDto<Boolean>> deleteTodo(@AuthenticationPrincipal Member member,
                                                           @PathVariable Long todoId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.deleteTodo(member, todoId), "투두 삭제 완료"));
    }

    @PutMapping("/{todoId}/status")
    public ResponseEntity<ResponseDto<Boolean>> updateTodoDone(@AuthenticationPrincipal Member member,
                                                               @PathVariable Long todoId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.updateTodoDone(member, todoId), "투두 상태 변경 완료"));
    }

    @PutMapping("/color/{colorCode}")
    public ResponseEntity<ResponseDto<String>> updateTodoColor(@AuthenticationPrincipal Member member,
                                                               @PathVariable String colorCode){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.updateTodoColor(member, colorCode), "투두 배경 색상 변경 완료"));
    }

    @GetMapping("/day/{date}")
    public ResponseEntity<ResponseDto<List<DayTodoResponseDto>>> getDayTodo(@AuthenticationPrincipal Member member,
                                                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.getDayTodo(member, date), "일별 투두 조회 완료"));
    }

    @GetMapping("/month")
    public ResponseEntity<ResponseDto<List<MonthTodoResponseDto>>> getMonthTodo(@AuthenticationPrincipal Member member,
                                                                                @Valid @RequestBody MonthTodoRequestDto req){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(todoService.getMonthTodo(member, req), "월별 투두 조회 완료"));
    }
}