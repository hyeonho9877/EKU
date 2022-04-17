package com.eku.EKU.repository;

import com.eku.EKU.domain.Lecture;
import com.eku.EKU.domain.LectureKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, LectureKey> {
}