package com.kyonggi.eku;

public class ListItem {
    String content;
    String time;

    public ListItem(String content, String time){
        this.content = content;
        this.time = time;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime(){
        return time;
    }

    public void setNumber(String content) {
        this.time = time;
    }
}
