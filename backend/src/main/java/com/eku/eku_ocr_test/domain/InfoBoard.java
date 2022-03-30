package com.eku.eku_ocr_test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id", nullable = false)
    private long iId;
    @ManyToMany(targetEntity = Member.class)
    @Column(name = "name", nullable = false)
    private List<String> name;
    @ManyToMany(targetEntity = Member.class)
    @Column(name = "department", nullable = false)
    private List<String> department;
    @Column(name = "lecture_building", nullable = false)
    private String lectureBuilding;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Column(name = "time", nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime time;

}
