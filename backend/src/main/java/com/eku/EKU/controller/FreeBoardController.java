package com.eku.EKU.controller;


import com.eku.EKU.domain.BoardList;
import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.exceptions.NoSuchBoardException;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.service.FreeBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/board/free/write")
    public ResponseEntity<?> insertBoard(@RequestBody FreeBoardForm form){
        try {
            return ResponseEntity.ok(boardService.insertBoard(form));
        }catch (NoSuchBoardException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }

    }

    /**
     * 게시물 수정 메소드
     * @param form 사용자 입력 폼
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/free/update")
    public ResponseEntity<?> updateBoard(@RequestBody FreeBoardForm form){
        try {
            boardService.updateBoard(form);
            return ResponseEntity.ok(form);
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form);
        }
    }

    /**
     * 게시판의 전체목록을 불러오는 메소드
     * @return 게시판목록(제목, id) list 반환
     */
    @GetMapping("/board/free/list")
    public ResponseEntity<?> boardList(){
        ArrayList<BoardList> list = boardService.boardList();
        if(!list.isEmpty())
            return ResponseEntity.ok(list);
        else
            return ResponseEntity.badRequest().body(null);
    }

    /**
     * 게시물을 불러오는 메소드
     * @param form
     * @return
     */
    @GetMapping("/board/free")
    public ResponseEntity<?> loadBoard(@RequestBody FreeBoardForm form){
        FreeBoard board = boardService.loadBoard(form);
        if(board!=null)
            return ResponseEntity.ok(board);
        else
            return ResponseEntity.badRequest().body(null);
    }
    /**
     * 게시물 삭제 메소드
     * @param form 삭제할 게시물
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/free/delete")
    public ResponseEntity<?> deleteBoard(@RequestBody FreeBoardForm form){
        try {
            boardService.deleteBoard(form.getId());
            return ResponseEntity.ok(form.getId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }
}
