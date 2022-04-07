package com.eku.eku_ocr_test.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * 자유게시판 댓글
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreeBoardComment {
    @Id
    @Column(name = "f_id", nullable = false)
    private long fId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "view", nullable = false)
    private int view;
    @Column(name = "time", nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime time;
    @ManyToOne
    private Student writer;
    @ManyToOne
    private FreeBoard original;
}
