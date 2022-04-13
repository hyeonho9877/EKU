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
public class InfoBoard {
    @Id
    @Column(name = "i_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long iId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "written_time", nullable = false)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime writtenTime;
    @Column(name = "building", nullable = false)
    private String building;

    @ManyToOne
    private Student writer;
}
