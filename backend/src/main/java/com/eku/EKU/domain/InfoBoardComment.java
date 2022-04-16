package com.eku.EKU.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class InfoBoardComment {
    @Id
    @Column(name = "ic_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long icId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "written_time", nullable = false)
    private String writtenTime;

    @ManyToOne
    private Student writer;
    @ManyToOne
    private InfoBoard original;

    @PrePersist
    public void onPrePersist(){
        this.writtenTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
