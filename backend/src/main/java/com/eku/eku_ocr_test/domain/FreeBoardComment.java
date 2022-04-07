package com.eku.eku_ocr_test.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "total_view", nullable = false, columnDefinition = "integer default 0")
    private int totalView;
    @Column(name = "written_time", nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime writtenTime;
    @ManyToOne
    private Student writer;
    @ManyToOne
    private FreeBoard original;
}
