package com.kyonggi.eku;

public class AnounceItem {

    private int id;
    private String writerName;
    private String writerNo;
    private String department;
    private String title;
    private String content;
    private String buliding;
    private String writtenTime;

    public String getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(String writtenTime) {
        this.writtenTime = writtenTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterNo() {
        return writerNo;
    }

    public void setWriterNo(String writerNo) {
        this.writerNo = writerNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBuliding() {
        return buliding;
    }

    public void setBuliding(String buliding) {
        this.buliding = buliding;
    }
}
