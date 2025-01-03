package org.todo.global.security.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.todo.global.security.jwt.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUsername(String username);
}