package com.eku.EKU.form;

import com.eku.EKU.domain.Student;
import lombok.Data;

@Data
public class StudentInfo {
    private Long studNo;
    private String department;

    public StudentInfo(Student student) {
        this.studNo = student.getStudNo();
        this.department = student.getDepartment();
    }
}
