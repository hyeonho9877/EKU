package com.kyonggi.eku;

public class Lecture {
    private String lectureName;
    private String professor;

    public Lecture(String lectureName,String professor){
        this.lectureName = lectureName;
        this.professor = professor;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }




}

