package com.eku.eku_ocr_test.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MappingKey implements Serializable {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no")
    private Student student;

    @Id
    @Column(name = "auth_key", nullable = false, unique = true)
    private String authKey;
}
