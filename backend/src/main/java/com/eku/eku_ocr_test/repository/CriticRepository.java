package com.eku.eku_ocr_test.repository;

import com.eku.eku_ocr_test.domain.Critic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriticRepository extends JpaRepository<Critic, Long> {
    Page<Critic> findAll(Pageable page);
}
