package com.kyonggi.eku;

public class FreeCommunityCommentItem {
    private String comment_id;
    private String writer;
    private String writer_no;
    private String comment;
    private String time;

    public FreeCommunityCommentItem(String comment_id, String writer, String comment, String time){
        this.comment_id = comment_id;
        this.writer = writer;
        this.writer_no = writer.substring(2,4) + "학번";
        this.comment = comment;
        this.time = time;
    }

    public FreeCommunityCommentItem(){

    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getComment() {
        return comment;
    }

    public String getComment_id() {
        return comment_id;
    }

    public String getTime() {
        return time;
    }

    public String getWriter() {
        return writer;
    }

    public String getWriter_no() { return writer_no; }
}
