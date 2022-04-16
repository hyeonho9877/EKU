package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@IdClass(MajorLectureKey.class)
public class MajorLecture {
    @Id
    private Integer year;
    @Id
    private String lectureNo;
    @Id
    private Integer semester;

    @Column(name = "campus", nullable = false)
    private String campus;
    @Column(name = "lecture_name", nullable = false)
    private String lectureName;
    @Column(name = "grade")
    private Short grade;
    @Column(name = "complete", nullable = false)
    private String complete;
    @Column(name = "point", nullable = false)
    private Short point;
    @Column(name = "professor", nullable = false)
    private String professor;
    @Column(name = "lecture_desc")
    private String lectureDesc;
    @Column(name = "lecture_time")
    private String lectureTime;
    @Column(name = "lecture_room")
    private String lectureRoom;
    @Column(name = "lecture_dept", nullable = false)
    private String dept;
}
