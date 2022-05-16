package com.kyonggi.eku.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;

public class OCRForm implements Serializable {
    private String name;
    private String department;
    private String studNo;

    public OCRForm() {
    }

    public OCRForm(String name, String department, String studNo) {
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

    public String getStudNo() {
        return studNo;
    }

    public void setStudNo(String studNo) {
        this.studNo = studNo;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.KOREA, "SignInForm(name:%s, department:%s, studNo:%s)", name, department, studNo);
    }


}
