package com.kyonggi.eku.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;

public class SignUpForm implements Serializable {
    private String name;
    private String department;
    private Long studNo;

    public SignUpForm() {
    }

    public SignUpForm(String name, String department, Long studNo) {
        this.name = name;
        this.department = department;
        this.studNo = studNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getStudNo() {
        return studNo;
    }

    public void setStudNo(Long studNo) {
        this.studNo = studNo;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.KOREA, "SignInForm(name:%s, department:%s, studNo:%d)", name, department, studNo);
    }
}
