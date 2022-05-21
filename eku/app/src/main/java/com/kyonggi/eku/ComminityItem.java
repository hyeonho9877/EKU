package com.kyonggi.eku;

public class ComminityItem {
    private String c_title;
    private String c_writer;
    private String c_comment;
    private String c_time;
    private String c_views;
    private String c_id;

    public ComminityItem(){

    }

    public ComminityItem(String id, String c_title, String c_writer, String c_comment, String c_time, String c_views){
        this.c_id      = id;
        this.c_title   = c_title;
        this.c_writer  = c_writer;
        this.c_comment = c_comment;
        this.c_time    = c_time;
        this.c_views   = c_views;
    }

    public ComminityItem(String id, String c_title){
        this.c_id      = id;
        this.c_title   = c_title;
    }


    public void setc_id(String c_id) {
        this.c_id = c_id;
    }

    public void setc_comment(String c_comment) {
        this.c_comment = c_comment;
    }

    public void setc_time(String c_time) {
        this.c_time = c_time;
    }

    public void setc_title(String c_title) {
        this.c_title = c_title;
    }

    public void setc_views(String c_views) {
        this.c_views = c_views;
    }

    public void setc_writer(String c_writer) {
        this.c_writer = c_writer;
    }

    public String getc_comment() {
        return c_comment;
    }

    public String getc_time() {
        return c_time;
    }

    public String getc_title() {
        return c_title;
    }

    public String getc_views() {
        return c_views;
    }

    public String getc_writer() {
        return c_writer;
    }

    public String getc_id() {
        return c_id;
    }
}
