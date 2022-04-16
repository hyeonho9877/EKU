package com.eku.EKU.repository;

import com.eku.EKU.domain.MajorLecture;
import com.eku.EKU.domain.MajorLectureKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorLectureRepository extends JpaRepository<MajorLecture, MajorLectureKey> {
}