package com.kyonggi.eku;

public class Lecture {
    private String lectureName;
    private String professor;
    private float star;
    private String content;

    public Lecture(String lectureName,String professor){
        this.lectureName = lectureName;
        this.professor = professor;
    }

    public Lecture(String lectureName,String professor, float star){
        this.lectureName = lectureName;
        this.professor = professor;
        this.star = star;
    }

    public Lecture(String lectureName, String professor, String content){
        this.lectureName = lectureName;
        this.professor = professor;
        this.content = content;
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

    public float getStar() { return star; }

    public void setStar(float star) {
        this.star = star;
    }

    public String getCtext() {
        return content;
    }

    public void setCtext(String content) {
        this.content = content;
    }

}

