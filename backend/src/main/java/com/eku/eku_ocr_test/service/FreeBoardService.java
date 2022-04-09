package com.eku.eku_ocr_test.service;

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
     */

}
