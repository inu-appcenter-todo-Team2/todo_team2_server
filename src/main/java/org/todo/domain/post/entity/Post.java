package org.todo.domain.post.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.todo.domain.global.BaseEntity;
import org.todo.domain.image.entity.Image;
import org.todo.domain.todo.entity.Todo;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "todo_id", nullable = false, unique = true)
    private Todo todo;

    @OneToOne
    @JoinColumn(name = "image_id", unique = true)
    private Image image;

    @Column(nullable = false)
    private String content;
}