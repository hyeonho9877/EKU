package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.FreeBoard;
import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.form.FreeBoardForm;
import com.eku.eku_ocr_test.repository.FreeBoardRepository;
import com.eku.eku_ocr_test.repository.StudentRepository;
import org.springframework.stereotype.Service;


/**
 * 게시판 정보를 불러오거나 수정, 삽입, 삭제하는 기능담당
 */
@Service
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final StudentRepository studentRepository;

    public FreeBoardService(FreeBoardRepository freeBoardRepository, StudentRepository studentRepository) {
        this.freeBoardRepository = freeBoardRepository;
        this.studentRepository = studentRepository;
    }
    /**
     * 게시판정보 삽입
     * @param form 삽입할 게시판의 정보
     */
    public void insertBoard(FreeBoardForm form){
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        Student studNo = Student.builder().studNo(form.getStudNo()).build();
        FreeBoard freeBoard = FreeBoard.builder()
                .id(form.getId())
                .student(studNo)
                .department(form.getDepartment())
                .title(form.getTitle())
                .content(form.getContent())
                .time(currentTime).build();
        freeBoardRepository.save(freeBoard);
    }

}
