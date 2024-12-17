package org.todo.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
