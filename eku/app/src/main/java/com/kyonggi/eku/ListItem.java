package com.kyonggi.eku;

public class ListItem {
    /**
     * 제작자 : 지웅 지웅이한테 물어보셈 <--
     * 제목
     * 잘모르겠습니다.
     * 내용
     * 스피너에 있는 어댑터에서 씀
     */
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
