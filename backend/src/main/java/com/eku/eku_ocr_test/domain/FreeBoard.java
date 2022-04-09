package com.eku.eku_ocr_test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;



/**
 * 자유게시판 정보 저장 테이블
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FreeBoard implements Serializable{
    @Id
    @Column(name = "f_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_no")
    private Student student;
    @Column(name = "student_department", nullable = false)
    private String department;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "total_view", nullable = false)
    private int view;
    @Column(name = "written_time", nullable = false)
    private String time;

    public FreeBoard() {

    }
    @Override
    public String toString(){
        return String.format("id=%d\nno=%d\ndep=%s\ntitle=%s\ncontent=%s\nview=%d\ntime=%s",id,student,department,title,content,view, time.toString());
    }
}
