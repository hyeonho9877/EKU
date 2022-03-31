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
@AllArgsConstructor
@NoArgsConstructor
public class FreeBoard {
    @Id
    @Column(name = "f_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fId;
    @ManyToMany(targetEntity = Member.class)
    @Column(name = "studNo", nullable = false)
    private List<Integer> studNo;
    @ManyToMany(targetEntity = Member.class)
    @Column(name = "department", nullable = false)
    private List<String> department;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Column(name = "views", nullable = false)
    private long views;
    @Column(name = "time", nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime time;
}
