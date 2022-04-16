package com.eku.EKU.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 자유게시판 댓글
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FreeBoardComment {
    @Id
    @Column(name = "f_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "written_time", nullable = false)
    @CreatedDate
    private String writtenTime;
    @ManyToOne
    private Student writer;
    @ManyToOne
    private FreeBoard original;

    @PrePersist
    public void onPrePersist(){
        this.writtenTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
