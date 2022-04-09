package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.FreeBoard;
import com.eku.eku_ocr_test.repository.FreeBoardRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;


/**
 * 게시판 정보를 불러오거나 수정, 삽입, 삭제하는 기능담당
 */
@Service
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;

    public FreeBoardService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }


}
