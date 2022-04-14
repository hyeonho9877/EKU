package com.kyonggi.eku;

public class TodoItem {

    /*
    제목 : todoitem


    기능 : todolist 추가되는 아이템


    */
    private int id;
    private String title;
    private String content;
    private String writeDate;

    public TodoItem () {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
