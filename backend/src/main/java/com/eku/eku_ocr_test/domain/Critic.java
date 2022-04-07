package com.eku.eku_ocr_test.domain;

import com.eku.eku_ocr_test.utils.GradeConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Critic {
    @Id
    @Column(name = "c_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cId;
    @Column(name = "lecture_name", nullable = false)
    private String lectureName;
    @Column(name = "prof_name", nullable = false)
    private String profName;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "grade", nullable = false)
    @Convert(converter = GradeConverter.class)
    private Grade grade;

    @ManyToOne
    private Student writer;
}
