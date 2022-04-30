package com.eku.EKU.repository;

import com.eku.EKU.domain.InfoBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoBoardCommentRepository extends JpaRepository<InfoBoardComment, Long> {
    List<InfoBoardComment> findAllByOriginalId(Long id);
}
