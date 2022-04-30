package com.kyonggi.eku;

public class CommentItem {

    private int articleID; // 게시글ID;
    private String content;
    private  String writer;
    private String writtenTime;

    public String getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(String writtenTime) {
        this.writtenTime = writtenTime;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
