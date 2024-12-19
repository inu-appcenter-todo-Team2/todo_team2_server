package org.todo.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.domain.image.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
