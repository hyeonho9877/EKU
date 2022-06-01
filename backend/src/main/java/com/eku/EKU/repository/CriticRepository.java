package com.eku.EKU.repository;

import com.eku.EKU.domain.Critic;
import com.eku.EKU.domain.LecturePrototype;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriticRepository extends JpaRepository<Critic, Long> {
    List<Critic> findByOrderByCriticIdDesc(Pageable pageable);
    List<Critic> findByLecture(LecturePrototype lecture);
}
