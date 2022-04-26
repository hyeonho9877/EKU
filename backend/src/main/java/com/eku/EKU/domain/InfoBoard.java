package com.eku.EKU.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoBoard {
    @Id
    @Column(name = "i_id", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "written_time", nullable = false)
    private String writtenTime;
    @Column(name = "building", nullable = false)
    private String building;

    @ManyToOne
    @JoinColumn(name = "writer_no")
    private Student no;
    @Column(name = "writer_department", nullable = false)
    private String department;
    @Column(name = "writer_name", nullable = false)
    private String name;
}
