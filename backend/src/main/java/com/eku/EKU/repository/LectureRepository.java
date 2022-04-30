package com.eku.EKU.repository;

import com.eku.EKU.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findByPrototypeLectureNameAndPrototypeProfessor(String time, String professor);
}