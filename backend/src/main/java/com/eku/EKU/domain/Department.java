package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 학과
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deptId;
    @Column(nullable = false)
    private String deptCode;
    @Column(nullable = false)
    private String deptName;
    @Column(nullable = false)
    private short year;
    @Column(nullable = false)
    private short semester;

    @ManyToOne
    private Faculty faculty;
    @ManyToOne
    private College college;
}
