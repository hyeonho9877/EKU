package com.kyonggi.eku;

public class LectureSchedule {
    private String LectureName="";
    private String LectureProfessor="";
    private String LectureBuilding="";


    public String getLectureProfessor() {
        return LectureProfessor;
    }

    public void setLectureProfessor(String lectureProfessor) {
        LectureProfessor = lectureProfessor;
    }

    public String getLectureName() {
        return LectureName;
    }

    public void setLectureName(String lectureName) {
        LectureName = lectureName;
    }

    public String getLectureBuilding() {
        return LectureBuilding;
    }

    public void setLectureBuilding(String lectureBuilding) {
        LectureBuilding = lectureBuilding;
    }
}
