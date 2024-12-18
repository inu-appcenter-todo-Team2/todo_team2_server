package org.todo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.domain.todo.dto.req.MonthTodoRequestDto;
import org.todo.domain.todo.dto.res.DayTodoResponseDto;
import org.todo.domain.todo.dto.res.MonthTodoResponseDto;
import org.todo.domain.member.entity.Member;
import org.todo.domain.member.service.MemberService;
import org.todo.domain.todo.dto.req.TodoCreateRequestDto;
import org.todo.domain.todo.dto.req.TodoUpdateRequestDto;
import org.todo.domain.todo.dto.res.TodoCreateResponseDto;
import org.todo.domain.todo.dto.res.TodoUpdateResponseDto;
import org.todo.domain.todo.entity.Todo;
import org.todo.domain.todo.repository.TodoRepository;
import org.todo.global.error.CustomErrorCode;
import org.todo.global.exception.RestApiException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberService memberService;

    @Transactional
    public TodoCreateResponseDto createTodo(Member member, TodoCreateRequestDto req){
        Todo todo = Todo.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .date(req.getDate())
                .priority(req.getPriority())
                .member(member)
                .build();

        todoRepository.save(todo);

        return TodoCreateResponseDto.builder()
                .email(member.getEmail())
                .title(todo.getTitle())
                .build();
    }

    @Transactional
    public TodoUpdateResponseDto updateTodo(Member member, Long todoId, TodoUpdateRequestDto req){

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.TODO_NOT_FOUND));

        checkAuthorization(todo, member);

        todo.updateTodo(req);

        return TodoUpdateResponseDto.builder()
                .email(member.getEmail())
                .title(todo.getTitle())
                .build();
    }

    @Transactional
    public Boolean deleteTodo(Member member, Long todoId){

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.TODO_NOT_FOUND));

        checkAuthorization(todo, member);

        todoRepository.deleteById(todoId);

        return true;
    }

    @Transactional
    public Boolean updateTodoDone(Member member, Long todoId){
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.TODO_NOT_FOUND));

        checkAuthorization(todo, member);

        todo.updateTodoDone();

        return todo.getDone();
    }

    @Transactional
    public String updateTodoColor(Member member, String colorCode){
        return memberService.updatePersonalColor(member, colorCode);
    }

    @Transactional(readOnly = true)
    public List<DayTodoResponseDto> getDayTodo(Member member, LocalDate date){
        return todoRepository.findByMemberIdAndDate(member.getId(), date)
                .stream()
                .map(todo -> DayTodoResponseDto.builder()
                        .todoId(todo.getId())
                        .title(todo.getTitle())
                        .description(todo.getDescription())
                        .done(todo.getDone())
                        .priority(todo.getPriority())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MonthTodoResponseDto> getMonthTodo(Member member, MonthTodoRequestDto req){

        LocalDate startDate = YearMonth.parse(req.getDate()).atDay(1);
        LocalDate endDate = startDate.plusMonths(1);

        List<Todo> todos = todoRepository.findByMemberEmailAndDateBetween(member.getEmail(), startDate, endDate);

        return todos.stream()
                .collect(Collectors.groupingBy(Todo::getDate))
                .entrySet().stream()
                .map(i -> MonthTodoResponseDto.builder()
                        .date(i.getKey())
                        .notDoneTodoCount((int) i.getValue().stream()
                                .filter(todo -> !todo.getDone())
                                .count())
                        .hasDoneTodo(i.getValue().stream()
                                .anyMatch(Todo::getDone))
                    .build())
                .sorted(Comparator.comparing(MonthTodoResponseDto::getDate))
                .collect(Collectors.toList());
    }

    public void checkAuthorization(Todo todo, Member member){
        if(!Objects.equals(todo.getMember().getUsername(), member.getUsername()))
            throw new RestApiException(CustomErrorCode.TODO_PERMISSION_DENIED);

        return;
    }
}