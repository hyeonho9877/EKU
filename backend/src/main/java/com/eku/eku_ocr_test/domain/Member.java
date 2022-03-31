package com.eku.eku_ocr_test.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Formatter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "stud_no", nullable = false)
    private int studNo;
    @Column(name="stud_name", nullable = false)
    private String name;
    @Column(name = "department", nullable = false)
    private String department;
    @Column(name = "email", nullable = false)
    private String email;


    public Member() {

    }

    @Override
    public String toString() {
        return String.format("name: %s\nstudNo: %d\ndepartment: %s\nemail: %s", name, studNo, department, email);
    }
}
