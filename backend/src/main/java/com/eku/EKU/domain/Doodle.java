package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doodle {
    @Id
    @Column(name = "doodle_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doodleId;
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    private Building building;
}
