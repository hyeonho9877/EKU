package com.eku.EKU.repository;

import com.eku.EKU.domain.InfoBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InfoBoardRepository extends JpaRepository<InfoBoard, Long> {

    @Query(value = "SELECT i FROM InfoBoard AS i where substring(i.building, :building, 1) = '1' order by i.writtenTime desc ")
    Page<InfoBoard> findAllByBuildingOrderByWrittenTime(@Param("building") int building, Pageable pageable);
    @Query(value = "SELECT i FROM InfoBoard AS i where substring(i.building, :building, 1) = '1' and i.id < :id order by i.writtenTime desc ")
    List<InfoBoard> findByBuildingAndIdIsLessThanOrderByWrittenTimeDesc(@NonNull int building, @NonNull Long id, Pageable pageable);
    @Query(value = "SELECT i FROM InfoBoard AS i where substring(i.building, :building, 1) = '1' and i.id > :id order by i.writtenTime desc ")
    List<InfoBoard> findByBuildingAndIdIsGreaterThanOrderByWrittenTimeDesc(@NonNull int building, @NonNull Long id);
    @Query(value = "SELECT i FROM InfoBoard AS i where substring(i.building, :building, 1) = '1' and (i.title like %:keyword% or i.content like %:keyword% or i.department like %:keyword%) order by i.writtenTime desc ")
    List<InfoBoard> findByBuildingAndKeywordOrderByWrittenTimeDesc(@NonNull int building, @NonNull String keyword, Pageable pageable);
    @Query(value = "SELECT i FROM InfoBoard AS i where substring(i.building, :building, 1) = '1' and (i.title like %:keyword% or i.content like %:keyword% or i.department like %:keyword%) and i.id < :id order by i.writtenTime desc ")
    List<InfoBoard> findByBuildingAndKeywordAndIdLessThanOrderByWrittenTimeDesc(@NonNull int building, @NonNull String keyword, @NonNull long id, @NonNull Pageable pageable);
    List<InfoBoard> findByOrderByWrittenTimeDesc(Pageable pageable);
    List<InfoBoard> findAllByIdLessThanOrderByWrittenTimeDesc(long id, Pageable pageable);

}
