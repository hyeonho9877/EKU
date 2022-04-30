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
@IdClass(Schedule.class)
public class Schedule implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "studNo", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Student studNo;

    @Id
    @Column(name = "password")
    private long password;

    @Id
    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Lecture lecture_id;

}
