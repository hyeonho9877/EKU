package com.eku.EKU.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 가입된 학생의 정보를 저장하는 테이블
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Student implements Serializable {
    @Id
    @Column(name = "no", nullable = false)
    private Long studNo;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "salt", nullable = false)
    private String salt;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name = "department", nullable = false)
    private String department;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    // 이메일 인증 성공 여부
    @Column(name = "authenticated", nullable = false)
    private boolean authenticated;

    public Student() {
        this.studNo = null;
        this.email = null;
    }

    @Override
    public String toString() {
        return String.format("name: %s\nstudNo: %d\ndepartment: %s\nemail: %s", name, studNo, department, email);
    }
}
