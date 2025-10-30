package com.github.birulazena.user_service.repository;

import com.github.birulazena.user_service.entities.CardInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    CardInfo save(CardInfo cardInfo);

    @Query(value = "SELECT * FROM card_info WHERE id = :id", nativeQuery = true)
    Optional<CardInfo> findById(@Param("id") Long id);

    Page<CardInfo> findAll(Pageable pageable);

    void deleteById(Long id);

}
