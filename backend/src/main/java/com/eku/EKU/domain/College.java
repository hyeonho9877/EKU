package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 대학 예)진성애교양대학
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long collegeId;
    @Column(nullable = false)
    private String collegeCode;
    @Column(nullable = false)
    private String collegeName;
    @Column(nullable = false)
    private short year;
    @Column(nullable = false)
    private String campus;
    @Column short semester;
}
