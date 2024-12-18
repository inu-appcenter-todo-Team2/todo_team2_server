package org.todo.domain.todo.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.todo.domain.global.BaseEntity;
import org.todo.domain.member.entity.Member;
import org.todo.domain.post.entity.Post;
import org.todo.domain.todo.dto.req.TodoUpdateRequestDto;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todo")
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityType priority;

    @Column(nullable = false)
    private Boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(mappedBy = "todo")
    private Post post;

    @Builder
    public Todo(Member member, String title, String description, LocalDate date, PriorityType priority){
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.member = member;
        this.done = false;
    }

    public void updateTodo(TodoUpdateRequestDto req){
        this.title = req.getTitle();
        this.description =req.getDescription();
        this.priority = req.getPriority();
    }

    public void updateTodoDone(){
        this.done = !this.done;
    }
}