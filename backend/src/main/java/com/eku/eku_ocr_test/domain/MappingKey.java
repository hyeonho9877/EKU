package com.eku.eku_ocr_test.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 사용자와 1대1 관계로 매핑되는 키 값을 저장하는 테이블
 * 키 값은 이메일 인증시에 발행됨
 */
@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MappingKey implements Serializable {

    // 사용자의 학번
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no")
    private Student student;

    // 서버가 발행한 사용자의 key
    @Id
    @Column(name = "auth_key", nullable = false, unique = true)
    private String authKey;
}
