package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 학부
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long facultyId;
    @Column(nullable = false)
    private String facultyCode;
    @Column(nullable = false)
    private String facultyName;
    @Column(nullable = false)
    private short year;
    @Column(nullable = false)
    private short semester;

    @ManyToOne
    private College college;
}
