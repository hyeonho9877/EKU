package com.eku.EKU.domain;

import com.eku.EKU.utils.LectureTypeConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@IdClass(LectureKey.class)
@Getter
@Setter
@ToString
public class Lecture {
    @Id
    private Integer year;
    @Id
    private String lectureNo;
    @Id
    private Integer semester;
    @Id
    @Convert(converter = LectureTypeConverter.class)
    private LectureType lectureType;

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
}
