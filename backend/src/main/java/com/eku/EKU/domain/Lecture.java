package com.eku.EKU.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    @Column(nullable = false)
    private String lectureNo;
    @Column(nullable = false)
    private String lectureTime;
    @Column(nullable = false)
    private String lectureRoom;
    @Column(nullable = false)
    private Short year;
    @Column(nullable = false)
    private Short semester;
    @Column(nullable = false)
    private String complete;
    private Short grade;
    @Column(nullable = false)
    private String lectureDesc;
    @Column(nullable = false)
    private Short point;

    @ManyToOne
    private LecturePrototype prototype;

}
