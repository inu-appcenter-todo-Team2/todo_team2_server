package org.todo.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.todo.domain.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.member.email = :email " +
            "AND FUNCTION('DATE', t.date) >= FUNCTION('DATE', :startDate) " +
            "AND FUNCTION('DATE', t.date) < FUNCTION('DATE', :endDate)")
    List<Todo> findByMemberEmailAndDateBetween(@Param("email") String email, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Todo> findByMemberIdAndDate(Long memberId, LocalDate date);

}