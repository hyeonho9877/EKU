package com.eku.eku_ocr_test.controller;

import com.eku.eku_ocr_test.service.FreeBoardService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시판 접근에 응답하는 컨트롤러
 */
@RestController
public class FreeBoardController {
    private final FreeBoardService boardService;


    public FreeBoardController(FreeBoardService boardService) {
        this.boardService = boardService;
    }

}
