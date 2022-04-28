package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureSubGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subGroupId;
    @Column(nullable = false)
    private Short groupCode;
    @Column(nullable = false)
    private String groupDesc;
    @Column(nullable = false)
    private Short year;
    @Column(nullable = false)
    private Short semester;

    @ManyToOne
    private LectureGroup superGroup;

}
