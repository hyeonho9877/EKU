package com.eku.EKU.repository;

import com.eku.EKU.domain.FreeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * FreeBoard에 대한 Repository
 */
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    List<FreeBoard> findByOrderByTimeDesc(Pageable pageable);
    List<FreeBoard> findByIdIsGreaterThanOrderByTimeDesc(Long id);
    List<FreeBoard> findByIdIsLessThanOrderByTimeDesc(Long id, Pageable ofSize);
    @Query(value = "SELECT i FROM FreeBoard AS i where i.content like %:keyword% or i.title like %:keyword% or i.content like %:keyword% or i.student.department like %:keyword% order by i.time desc ")
    List<FreeBoard> findByKeywordOrderByTimeDesc(@NonNull String keyword, Pageable pageable);
    @Query(value = "SELECT i FROM FreeBoard AS i where (i.content like %:keyword% or i.title like %:keyword% or i.content like %:keyword% or i.student.department like %:keyword%) and i.id < :id order by i.time desc ")
    List<FreeBoard> findByKeywordAndIdLessThanOrderByTimeDesc(@NonNull String keyword, @NonNull long id, Pageable pageable);
}
