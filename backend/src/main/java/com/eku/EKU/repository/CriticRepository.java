package com.eku.EKU.repository;

import com.eku.EKU.domain.Critic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriticRepository extends JpaRepository<Critic, Long> {
    Page<Critic> findAll(Pageable page);
}
