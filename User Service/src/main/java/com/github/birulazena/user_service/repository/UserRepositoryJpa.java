package com.github.birulazena.user_service.repository;

import com.github.birulazena.user_service.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {

    User save(User user);

    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u")
    @EntityGraph(attributePaths = "cards")
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "cards")
    Optional<User> getUserByEmail(String email);

//    Для обновления использовать меод save

    void deleteById(Long id);
}
