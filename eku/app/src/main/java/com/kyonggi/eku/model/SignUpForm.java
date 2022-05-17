package com.kyonggi.eku.model;

import androidx.annotation.NonNull;

import java.util.Locale;

public class SignUpForm {
    private String name;
    private Long studNo;
    private String password;
    private String department;
    private String email;

    public SignUpForm() {
    }

    public SignUpForm(String name, Long studNo, String password, String department, String email) {
        this.name = name;
        this.studNo = studNo;
        this.password = password;
        this.department = department;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStudNo() {
        return studNo;
    }

    public void setStudNo(Long studNo) {
        this.studNo = studNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.KOREA, "SignUpForm(studNo=%d, name=%s, department=%s, email=%s, password=%s", studNo, name, department, email, password);
    }
}
