package com.eku.EKU.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Doodle {
    @Id
    @Column(name = "doodle_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doodleId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "written_time",nullable = false)
    @CreatedDate
    private String writtenTime;

    @ManyToOne
    private Building building;

    @PrePersist
    public void onPrePersist(){
        this.writtenTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
