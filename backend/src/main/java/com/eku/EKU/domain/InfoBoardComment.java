package com.eku.EKU.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoBoardComment {
    @Id
    @Column(name = "ic_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long icId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "written_time", nullable = false)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime writtenTime;

    @ManyToOne
    private Student writer;
    @ManyToOne
    private InfoBoard original;
}
