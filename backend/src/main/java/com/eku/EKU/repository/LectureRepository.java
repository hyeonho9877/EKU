package com.eku.EKU.repository;

import com.eku.EKU.domain.GELecture;
import com.eku.EKU.domain.GELectureKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<GELecture, GELectureKey> {
}