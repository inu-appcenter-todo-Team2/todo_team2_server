package org.todo.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.todo.domain.post.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findTop3ByOrderByIdDesc();
    List<Post> findTop3ByIdLessThanOrderByIdDesc(Long id);
    @Query("SELECT p FROM Post p JOIN p.todo t WHERE t.member.id = :memberId ORDER BY p.id DESC")
    List<Post> findAllByMemberIdOrderByIdDesc(@Param("memberId") Long memberId);
}