package com.kyonggi.eku.model;

public class FreeBoardPreview extends BoardPreview{
    private Integer comments;

    public FreeBoardPreview(Long id, String writer, String title, Integer no, String time, Integer view, Integer comments) {
        super(id, writer, title, no, time, view);
        this.comments = comments;
    }

    public FreeBoardPreview(BoardPreview preview) {
        super(preview.getId(), preview.getWriter(), preview.getTitle(), preview.getNo(), preview.getTime(), preview.getView());
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
