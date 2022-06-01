package com.eku.EKU.controller;


import com.eku.EKU.exceptions.NoSuchArticleException;
import com.eku.EKU.exceptions.NoSuchBoardException;
import com.eku.EKU.exceptions.NoSuchStudentException;
import com.eku.EKU.form.*;
import com.eku.EKU.service.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 게시판 접근에 응답하는 컨트롤러
 */
@RestController
public class BoardController {
    private final FreeBoardService freeBoardService;
    private final InfoBoardService infoBoardService;
    private final FreeBoardCommentService freeBoardCommentService;
    private final InfoBoardCommentService infoBoardCommentService;
    private final FileService fileService;

    public BoardController(FreeBoardService boardService, InfoBoardService infoBoardService, FreeBoardCommentService freeBoardCommentService, InfoBoardCommentService infoBoardCommentService, FileService fileService) {
        this.freeBoardService = boardService;
        this.infoBoardService = infoBoardService;
        this.freeBoardCommentService = freeBoardCommentService;
        this.infoBoardCommentService = infoBoardCommentService;
        this.fileService = fileService;
    }

    /**
     * 자유 게시판 삽입 메소드
     *
     * @param form 사용자 입력 폼
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/board/free/write")
    public ResponseEntity<?> insertBoard(@RequestBody FreeBoardForm form) {
        try {
            return ResponseEntity.ok(freeBoardService.insertBoard(form));
        } catch (NoSuchBoardException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("no such element");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("illegal");
        }
    }

    /**
     * 자유 게시판 수정 메소드
     *
     * @param form 사용자 입력 폼
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/free/update")
    public ResponseEntity<?> updateBoard(@RequestBody FreeBoardForm form) {
        try {
            freeBoardService.updateBoard(form);
            return ResponseEntity.ok("success");
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("article is not exist");
        }
    }

    /**
     * 자유게시판의 전체목록을 불러오는 메소드
     *
     * @return 게시판목록 list 반환
     */
    @PostMapping("/board/free/lists")
    public ResponseEntity<?> freeBoardList() {
        List<BoardListResponse> list = freeBoardService.boardList();
        if (!list.isEmpty())
            return ResponseEntity.ok(list);
        else
            return ResponseEntity.ok("list is empty");
    }

    /**
     * 자유 게시판 게시물을 불러오는 메소드
     *
     * @param form
     * @return
     */
    @PostMapping("/board/free/view")
    public ResponseEntity<?> loadBoard(@RequestBody FreeBoardForm form) {
        try {
            FreeBoardResponse board = new FreeBoardResponse(freeBoardService.loadBoard(form));
            List<FreeBoardCommentResponse> commentList = freeBoardCommentService.commentList(form.getId());
            board.setCommentList(commentList);
            return ResponseEntity.ok(board);
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.ok("no articles");
        }
    }

    /**
     * 자유게시판 삭제 메소드
     *
     * @param form 삭제할 게시물
     * @return
     */
    @PostMapping("/board/free/delete")
    public ResponseEntity<?> deleteBoard(@RequestBody FreeBoardForm form) {
        try {
            freeBoardService.deleteBoard(form.getId());
            return ResponseEntity.ok(form.getId());
        } catch (IllegalArgumentException | EmptyResultDataAccessException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.ok("no articles");
        }
    }

    /**
     * 공지 게시판 삽입 메소드
     *
     * @param form 사용자 입력 폼
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/board/info/write")
    public ResponseEntity<?> insertBoard(@RequestBody InfoBoardForm form) {
        try {
            return ResponseEntity.ok(infoBoardService.insertBoard(form));
        } catch (NoSuchBoardException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("no such board");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("building code error");
        }
    }

    /**
     * 공지게시판 수정 메소드
     *
     * @param form 사용자 입력 폼
     * @return 성공시 true, 실패시 false 반환
     */
    @PostMapping("/board/info/update")
    public ResponseEntity<?> updateBoard(@RequestBody InfoBoardForm form) {
        try {
            infoBoardService.updateBoard(form);
            return ResponseEntity.ok(form.getId());
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(form.getId());
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }

    /**
     * 공지게시판 삭제 메소드
     *
     * @param form 삭제할 게시물
     * @return
     */
    @PostMapping("/board/info/delete")
    public ResponseEntity<?> deleteBoard(@RequestBody InfoBoardForm form) {
        try {
            fileService.deleteFile(form.getId());
            infoBoardService.deleteBoard(form.getId());
            return ResponseEntity.ok(form.getId());
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(form.getId());
        } catch (IllegalArgumentException | EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getId());
        }
    }

    /**
     * 공지게시판의 전체목록을 불러오는 메소드
     *
     * @return 게시판목록 list 반환
     */
    @PostMapping("/board/info/lists")
    public ResponseEntity<?> infoBoardList(@RequestBody BoardListForm form) {
        List<BoardListResponse> list = infoBoardService.boardList(form);
        return ResponseEntity.ok(list);
    }

    /**
     * 공지 게시판 게시물을 불러오는 메소드
     *
     * @param form
     * @return
     */
    @PostMapping("/board/info/view")
    public ResponseEntity<?> loadBoard(@RequestBody InfoBoardForm form) {
        try {
            InfoBoardResponse board = new InfoBoardResponse(infoBoardService.loadBoard(form));
            List<InfoBoardCommentResponse> commentList = infoBoardCommentService.commentList(form.getId());
            board.setCommentList(commentList);
            board.setImageList(fileService.imageURL(form.getId()));
            return ResponseEntity.ok(board);
        } catch (EmptyResultDataAccessException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("no article");
        }
    }

    @PostMapping("/board/info/recent")
    public ResponseEntity<?> latestInfoBoard(@RequestBody InfoBoardForm form) {
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        try {
            List<BoardListResponse> articles = infoBoardService.getRecentBoard(form.getLectureBuilding(), form.getId());
            formData.add("articles", articles);
            formData.add("images", infoBoardService.imageURL(articles));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "multipart/form-data;boundary=----WebKitFormB~~3");
            return new ResponseEntity<>(formData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/board/info/load")
    public ResponseEntity<?> loadInfoBoard(@RequestBody InfoBoardForm form) {
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        try {
            List<BoardListResponse> articles = infoBoardService.loadBoardAfterId(form.getLectureBuilding(), form.getId());
            formData.add("articles", articles);
            formData.add("images", infoBoardService.imageURL(articles));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "multipart/form-data;boundary=----WebKitFormB~~3");
            return new ResponseEntity<>(formData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/board/free/recent")
    public ResponseEntity<?> latestFreeBoard(@RequestBody InfoBoardForm form) {
        return ResponseEntity.ok(freeBoardService.getRecentBoard(form.getId()));
    }

    @PostMapping("/board/free/load")
    public ResponseEntity<?> loadFreeBoard(@RequestBody InfoBoardForm form) {
        return ResponseEntity.ok(freeBoardService.loadBoardAfterId(form.getId()));
    }

    @PostMapping("/board/free/search")
    public ResponseEntity<?> searchFreeBoard(@RequestBody FreeBoardForm form) {
        return ResponseEntity.ok(freeBoardService.searchBoard(form.getKeyword()));
    }

    @PostMapping("/board/info/search")
    public ResponseEntity<?> searchInfoBoard(@RequestBody InfoBoardForm form) {
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        try {
            List<BoardListResponse> articles = infoBoardService.searchBoard(form.getLectureBuilding(), form.getKeyword());
            formData.add("articles", articles);
            formData.add("images", infoBoardService.imageURL(articles));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "multipart/form-data;boundary=----WebKitFormB~~3");
            return new ResponseEntity<>(formData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/board/info/search/load")
    public ResponseEntity<?> searchMoreInfoBoard(@RequestBody InfoBoardForm form) {
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        try {
            List<BoardListResponse> articles = infoBoardService.searchMoreBoard(form.getLectureBuilding(), form.getKeyword(), form.getId());
            formData.add("articles", articles);
            formData.add("images", infoBoardService.imageURL(articles));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "multipart/form-data;boundary=----WebKitFormB~~3");
            return new ResponseEntity<>(formData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/board/free/search/load")
    public ResponseEntity<?> searchMoreFreeBoard(@RequestBody FreeBoardForm form) {
        return ResponseEntity.ok(freeBoardService.searchMoreBoard(form.getKeyword(), form.getId()));
    }

    @PostMapping("/board/info/preview")
    public ResponseEntity<?> previewInfoBoard(@RequestBody InfoBoardForm form) {
        return ResponseEntity.ok(infoBoardService.previewInfoBoard(form.getLectureBuilding()));
    }

    @PostMapping("/board/free/preview")
    public ResponseEntity<?> previewFreeBoard() {
        return ResponseEntity.ok(freeBoardService.previewFreeBoard());
    }

    @PostMapping("/board/info/all")
    public ResponseEntity<?> allInfo() {
        return ResponseEntity.ok(infoBoardService.allInfoBoard());
    }

    @PostMapping("/board/info/all/load")
    public ResponseEntity<?> allInfoMore(@RequestBody InfoBoardForm form) {
        return ResponseEntity.ok(infoBoardService.allInfoBoardMore(form.getId()));
    }

    /**
     * 자유 게시판 댓글 작성
     *
     * @param form 작성하려는 댓글의 정보를 담고 있는 Form 객체
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/comment/free/write")
    public ResponseEntity<?> writeFreeComment(@RequestBody CommentForm form) {
        try {
            freeBoardCommentService.writeComment(form);
            return ResponseEntity.ok(new FreeBoardCommentResponse(form));
        } catch (NoSuchStudentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("no such");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("illegal");
        }
    }

    /**
     * 자유 게시판 댓글 삭제 메소드
     *
     * @param form 삭제하려는 댓글의 정보가 담긴 Form 객체
     * @return 성공 -> ok와 함께 삭제한 댓글의 id, 실패 -> internal server error와 함께 삭제에 실패한 댓글의 id
     */
    @PostMapping("/comment/free/delete")
    public ResponseEntity<?> deleteFreeComment(@RequestBody CommentForm form) {
        System.out.println(form);
        try {
            freeBoardCommentService.deleteComment(form);
            return ResponseEntity.ok(form.getCommentId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getCommentId());
        }
    }

    /**
     * 자유 게시판 댓글 수정 메소드
     *
     * @param form 수정하려는 댓글의 정보가 담긴 Form 객체
     * @return 성공 -> ok와 함께 수정한 댓글의 id, 실패 -> bad request와 함께 수정에 실패한 댓글의 id
     */
    @PostMapping("/comment/free/update")
    public ResponseEntity<?> updateFreeComment(@RequestBody CommentForm form) {
        try {
            freeBoardCommentService.updateComment(form);
            return ResponseEntity.ok(form.getCommentId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(form.getCommentId());
        }
    }

    @PostMapping("/comment/info/write")
    public ResponseEntity<?> writeInfoComment(@RequestBody CommentForm form) {
        try {
            infoBoardCommentService.applyComment(form);
            return ResponseEntity.ok(new InfoBoardCommentResponse(form));
        } catch (NoSuchStudentException | NoSuchArticleException exception) {
            return ResponseEntity.internalServerError().body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/comment/info/delete")
    public ResponseEntity<?> deleteInfoComment(@RequestBody CommentForm form) {
        try {
            infoBoardCommentService.deleteComment(form);
            return ResponseEntity.ok(form.getCommentId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(form.getCommentId());
        }
    }

    @PostMapping("/comment/info/update")
    public ResponseEntity<?> updateInfoComment(@RequestBody CommentForm form) {
        try {
            infoBoardCommentService.updateComment(form);
            return ResponseEntity.ok(form.getCommentId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(form.getCommentId());
        }
    }

    /**
     * 공지게시판 사진업로드
     *
     * @param files 이미지 파일
     * @param id    해당 공지게시판 id
     * @return
     */
    @PostMapping("/file/upload")
    public ResponseEntity<?> fileUpload(@RequestParam(value = "image") List<MultipartFile> files, @RequestParam(value = "id") Long id) {
        try {
            for (MultipartFile i : files)
                fileService.fileUpload(i, id);
            return ResponseEntity.ok("success");
        } catch (IOException | IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body("fail");
        }
    }

    /**
     * 이미지 파일의 url
     *
     * @param id
     * @param imageNo
     * @return
     */
    @GetMapping("/board/info/image")
    public ResponseEntity<?> imageTest(@RequestParam(value = "id") Long id, @RequestParam(value = "imageNo") int imageNo) {
        try {
            List<String> fileName = fileService.imageList(id);
            Resource resource = new FileSystemResource(fileName.get(0));
            if (!resource.exists()) {
                return ResponseEntity.badRequest().body("file is not exist"); // 리턴 결과 반환 404
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            try {
                filePath = Paths.get(fileName.get(imageNo));
                header.add("Content-Type", Files.probeContentType(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("file error");
        }
    }

    @PostMapping("/board/info/image.list")
    public ResponseEntity<MultiValueMap<String, Object>> imageTest(@RequestBody BoardListForm form) {
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        try {
            List<BoardListResponse> articles = infoBoardService.boardList(form);
            formData.add("articles", articles);
            formData.add("images", infoBoardService.imageURL(articles));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "multipart/form-data;boundary=----WebKitFormB~~3");
            return new ResponseEntity<>(formData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}

