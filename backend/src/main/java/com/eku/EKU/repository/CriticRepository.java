package com.eku.EKU.repository;

import com.eku.EKU.domain.Critic;
import com.eku.EKU.domain.LecturePrototype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriticRepository extends JpaRepository<Critic, Long> {
    Page<Critic> findAll(Pageable page);
    List<Critic> findByLecture(LecturePrototype lecture);
}
