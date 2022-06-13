package com.kyonggi.eku;

public class ListItem {
    /**
     그리드뷰에서 씀
     **/
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

    public void setTime(String content) {
        this.time = time;
    }
}
