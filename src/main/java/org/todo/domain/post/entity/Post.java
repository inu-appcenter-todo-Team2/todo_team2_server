package org.todo.domain.post.entity;
import jakarta.persistence.*;
import lombok.*;
import org.todo.domain.global.BaseEntity;
import org.todo.domain.image.entity.Image;
import org.todo.domain.todo.entity.Todo;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "todo_id", nullable = false, unique = true)
    private Todo todo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", unique = true)
    private Image image;
}