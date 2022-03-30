package com.eku.eku_ocr_test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Critic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cId", nullable = false)
    private Long c_id;
    @Column(name = "lecture_name", nullable = false)
    private String lectureName;
    @Column(name = "professor", nullable = false)
    private String professor;
    @ManyToMany(targetEntity = Member.class)
    private List<Integer> studNo;
    @ManyToMany(targetEntity = Member.class)
    private List<String> department;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Column(name = "grade", nullable = false)
    private float grade;
}
