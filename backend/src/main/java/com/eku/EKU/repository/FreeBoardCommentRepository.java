package com.eku.EKU.repository;


import com.eku.EKU.domain.FreeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 자유게시판에 대한 repo
 */
public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {
}
