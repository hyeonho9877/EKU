package com.eku.EKU.controller;


import com.eku.EKU.domain.BoardList;
import com.eku.EKU.domain.FreeBoardResponse;
import com.eku.EKU.domain.InfoBoardResponse;
import com.eku.EKU.exceptions.NoSuchBoardException;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.form.InfoBoardForm;
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
    private final FreeBoardService freeBoardService;
    private final InfoBoardService infoBoardService;
    
    public BoardController(FreeBoardService boardService, InfoBoardService infoBoardService) {
        this.freeBoardService = boardService;
        this.infoBoardService = infoBoardService;
    }

    /**
     * 자유 게시판 삽입 메소드
     * @param form 사용자 입력 폼
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/board/free/write")
    public ResponseEntity<?> insertBoard(@RequestBody FreeBoardForm form){
        try {
            return ResponseEntity.ok(freeBoardService.insertBoard(form));
        }catch (NoSuchBoardException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * 자유 게시판 수정 메소드
     * @param form 사용자 입력 폼
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/free/update")
    public ResponseEntity<?> updateBoard(@RequestBody FreeBoardForm form){
        try {
            freeBoardService.updateBoard(form);
            return ResponseEntity.ok(form.getId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }

    /**
     * 게시판의 전체목록을 불러오는 메소드
     * @return 게시판목록 list 반환
     */
    @PostMapping("/board/free/lists")
    public ResponseEntity<?> boardList(){
        ArrayList<BoardList> list = freeBoardService.boardList();
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
    @PostMapping("/board/free/view")
    public ResponseEntity<?> loadBoard(@RequestBody FreeBoardForm form){
        FreeBoardResponse board = new FreeBoardResponse(freeBoardService.loadBoard(form));
        if(board!=null)
            return ResponseEntity.ok(board);
        else
            return ResponseEntity.badRequest().body(board.getId());
    }
    /**
     * 자유게시판 삭제 메소드
     * @param form 삭제할 게시물
     * @return
     */
    @PostMapping("/board/free/delete")
    public ResponseEntity<?> deleteBoard(@RequestBody FreeBoardForm form){
        try {
            freeBoardService.deleteBoard(form.getId());
            return ResponseEntity.ok(form.getId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }
    /**
     * 공지 게시판 삽입 메소드
     * @param form 사용자 입력 폼
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/board/info/write")
    public ResponseEntity<?> insertBoard(@RequestBody InfoBoardForm form){
        try {
            return ResponseEntity.ok(infoBoardService.insertBoard(form));
        }catch (NoSuchBoardException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
    /**
     * 공지게시판 수정 메소드
     * @param form 사용자 입력 폼
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/info/update")
    public ResponseEntity<?> updateBoard(@RequestBody InfoBoardForm form){
        try {
            infoBoardService.updateBoard(form);
            return ResponseEntity.ok(form.getId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }
    /**
     * 공지게시판 삭제 메소드
     * @param form 삭제할 게시물
     * @return
     */
    @PostMapping("/board/info/delete")
    public ResponseEntity<?> deleteBoard(@RequestBody InfoBoardForm form){
        try {
            infoBoardService.deleteBoard(form.getId());
            return ResponseEntity.ok(form.getId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }
    /**
     * 공지게시판의 전체목록을 불러오는 메소드
     * @return 게시판목록 list 반환
     */
    @PostMapping("/board/info/lists")
    public ResponseEntity<?> boardList(@RequestBody InfoBoardForm form){
        ArrayList<BoardList> list = infoBoardService.boardList(form);
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
    @PostMapping("/board/info/view")
    public ResponseEntity<?> loadBoard(@RequestBody InfoBoardForm form){
        InfoBoardResponse board = new InfoBoardResponse(infoBoardService.loadBoard(form));
        if(board!=null)
            return ResponseEntity.ok(board);
        else
            return ResponseEntity.badRequest().body(board.getId());
    }
}

