package org.todo.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
