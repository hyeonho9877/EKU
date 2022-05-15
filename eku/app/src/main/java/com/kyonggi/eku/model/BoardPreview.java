package com.kyonggi.eku.model;

import androidx.annotation.Nullable;

public class BoardPreview {
    private Long id;
    private String writer;
    private String title;
    private Integer no;
    private String time;
    private Integer view;

    public BoardPreview() {
    }

    public BoardPreview(Long id, String writer, String title, Integer no, String time, Integer view) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.no = no;
        this.time = time;
        this.view = view;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.id.equals(((BoardPreview) obj).getId());
    }
}
