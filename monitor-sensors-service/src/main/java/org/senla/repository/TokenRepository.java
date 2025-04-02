package org.senla.repository;

import org.senla.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
        SELECT token FROM Token token INNER JOIN User user ON token.user.id = user.id
        WHERE user.id = :userId AND (token.expired = false OR token.revoked = false)
    """)
    List<Token> findAllValidTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);
}