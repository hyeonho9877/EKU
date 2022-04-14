package com.eku.EKU.controller;

import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.form.BoardListForm;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.service.FreeBoardService;
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
     * 게시물 수정 메소드
     * @param form 사용자 입력 폼
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/free/edit")
    public boolean editBoard(@RequestBody FreeBoardForm form){
        return boardService.editBoard(form);
    }

    /**
     * 게시판의 전체목록을 불러오는 메소드
     * @return 게시판목록(제목, id) list 반환
     */
    @GetMapping("/board/free/list")
    public ArrayList<BoardListForm> boardList(){
       return boardService.boardList();
    }
    /**
     * 게시물 삭제 메소드
     * @param id 삭제할 게시물 번호
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/free/delete")
    public boolean deleteBoard(@RequestBody Long id){
        return boardService.deleteBoard(id);
    }
}
