package com.eku.eku_ocr_test.controller;

import com.eku.eku_ocr_test.domain.FreeBoard;
import com.eku.eku_ocr_test.form.BoardListForm;
import com.eku.eku_ocr_test.form.FreeBoardForm;
import com.eku.eku_ocr_test.service.FreeBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 게시판 접근에 응답하는 컨트롤러
 */
@RestController
public class FreeBoardController {
    private final FreeBoardService boardService;
    public FreeBoardController(FreeBoardService boardService) {
        this.boardService = boardService;
    }
    /**
     * 게시물 삽입 메소드
     * @param form 사용자 입력 폼
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/free/write")
    public boolean insertBoard(@RequestBody FreeBoardForm form){
        return boardService.insertBoard(form);
    }

    /**
     * 게시판의 전체목록을 불러오는 메소드
     * @return 게시판목록(제목, id) list 반환
     */
    @GetMapping("/board/free/list")
    public ArrayList<BoardListForm> boardList(){
       return boardService.boardList();
    }
}
