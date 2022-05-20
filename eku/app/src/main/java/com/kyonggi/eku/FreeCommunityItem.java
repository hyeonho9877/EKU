package com.kyonggi.eku;

public class FreeCommunityItem {
    private String fc_title;
    private String fc_writer;
    private String fc_comment;
    private String fc_time;
    private String fc_views;
    private String fc_id;

    public FreeCommunityItem(){

    }
    public FreeCommunityItem(String id, String fc_title, String fc_writer, String fc_comment, String fc_time, String fc_views){
        this.fc_id      = id;
        this.fc_title   = fc_title;
        this.fc_writer  = fc_writer;
        this.fc_comment = fc_comment;
        this.fc_time    = fc_time;
        this.fc_views   = fc_views;
    }

    public void setFc_id(String fc_id) {
        this.fc_id = fc_id;
    }

    public void setFc_comment(String fc_comment) {
        this.fc_comment = fc_comment;
    }

    public void setFc_time(String fc_time) {
        this.fc_time = fc_time;
    }

    public void setFc_title(String fc_title) {
        this.fc_title = fc_title;
    }

    public void setFc_views(String fc_views) {
        this.fc_views = fc_views;
    }

    public void setFc_writer(String fc_writer) {
        this.fc_writer = fc_writer;
    }

    public String getFc_comment() {
        return fc_comment;
    }

    public String getFc_time() {
        return fc_time;
    }

    public String getFc_title() {
        return fc_title;
    }

    public String getFc_views() {
        return fc_views;
    }

    public String getFc_writer() {
        return fc_writer;
    }

    public String getFc_id() {
        return fc_id;
    }
}
