package com.eku.EKU.controller;


import com.eku.EKU.domain.BoardList;
import com.eku.EKU.domain.FreeBoardResponse;
import com.eku.EKU.exceptions.NoSuchBoardException;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.service.FreeBoardService;
import com.eku.EKU.service.InfoBoardService;
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
public class BoardController {
    private final FreeBoardService freeBoardServicee;
    private final InfoBoardService infoBoardService;
    
    public BoardController(FreeBoardService boardService, InfoBoardService infoBoardService) {
        this.freeBoardServicee = boardService;
        this.infoBoardService = infoBoardService;
    }

    /**
     * 게시물 삽입 메소드
     * @param form 사용자 입력 폼
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/board/free/write")
    public ResponseEntity<?> insertBoard(@RequestBody FreeBoardForm form){
        try {
            return ResponseEntity.ok(freeBoardServicee.insertBoard(form));
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
            freeBoardServicee.updateBoard(form);
            return ResponseEntity.ok(form.getId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }

    /**
     * 게시판의 전체목록을 불러오는 메소드
     * @return 게시판목록(제목, id) list 반환
     */
    @GetMapping("/board/free/lists")
    public ResponseEntity<?> boardList(){
        ArrayList<BoardList> list = freeBoardServicee.boardList();
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
    @GetMapping("/board/free/view")
    public ResponseEntity<?> loadBoard(@RequestBody FreeBoardForm form){
        FreeBoardResponse board = new FreeBoardResponse(freeBoardServicee.loadBoard(form));
        if(board!=null)
            return ResponseEntity.ok(board);
        else
            return ResponseEntity.badRequest().body(board.getId());
    }
    /**
     * 게시물 삭제 메소드
     * @param form 삭제할 게시물
     * @return
     */
    @PostMapping("/board/free/delete")
    public ResponseEntity<?> deleteBoard(@RequestBody FreeBoardForm form){
        try {
            freeBoardServicee.deleteBoard(form.getId());
            return ResponseEntity.ok(form.getId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }
}
