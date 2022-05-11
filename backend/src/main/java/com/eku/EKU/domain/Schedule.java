package com.eku.EKU.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 시간표
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "stud_No", nullable = false)
    private Student student;

    @Column(name = "password")
    private long password;

    @Column(name = "lecture_name")
    private String lectureName;

    @Column(name = "lecture_time")
    private String lectureTime;

    @Column(name = "lecture_room")
    private String lectureRoom;

    @Column(name = "professor")
    private String professor;

}
