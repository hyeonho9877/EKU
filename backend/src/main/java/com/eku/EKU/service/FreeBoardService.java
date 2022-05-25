package com.eku.EKU.service;

import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.BoardListResponse;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.form.FreeBoardListResponse;
import com.eku.EKU.form.FreeBoardResponse;
import com.eku.EKU.repository.FreeBoardRepository;
import com.eku.EKU.utils.RelativeTimeConverter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * 게시판 정보를 불러오거나 수정, 삽입, 삭제하는 기능담당
 */
@Service
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;

    public FreeBoardService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }

    /**
     * 게시물 불러오기
     *
     * @param form
     * @return
     */
    public FreeBoard loadBoard(FreeBoardForm form) throws IllegalArgumentException, NoSuchElementException {
        FreeBoard board = freeBoardRepository.findById(form.getId()).orElseThrow();
        board.setView(board.getView() + 1);
        freeBoardRepository.save(board);

        return board;
    }

    /**
     * 게시물 목록
     *
     * @return List<FreeBoard>로 목록을 반환
     */
    public List<BoardListResponse> boardList() throws IllegalArgumentException, NoSuchElementException {
        List<FreeBoard> list = freeBoardRepository.findByOrderByTimeDesc(Pageable.ofSize(20));
        List<BoardListResponse> newList = new ArrayList<BoardListResponse>();
        for (FreeBoard i : list) {
            Student student = i.getStudent();
            String writer = studentNo(student.getStudNo()) + " " + student.getDepartment();
            FreeBoardListResponse form = FreeBoardListResponse
                    .builder()
                    .id(i.getId())
                    .no(student.getStudNo())
                    .writer(writer)
                    .comments(i.getComments())
                    .time(RelativeTimeConverter.convertToRelativeTime(i.getTime()))
                    .title(i.getTitle())
                    .view(i.getView())
                    .build();
            newList.add(form);
        }
        return newList;
    }

    /**
     * 게시판정보 수정
     *
     * @param form 수정할 게시판의 정보
     */
    public void updateBoard(FreeBoardForm form) throws IllegalArgumentException, NoSuchElementException {
        FreeBoard board = freeBoardRepository.findById(form.getId()).get();
        if (form.getTitle() != null && form.getContent() != null) {
            board.setContent(form.getContent());
            board.setTitle(form.getTitle());
            freeBoardRepository.save(board);
        }
    }

    /**
     * 게시물 삭제
     *
     * @param id 해당 게시물 번호
     */
    public void deleteBoard(Long id) throws IllegalArgumentException, NoSuchElementException, EmptyResultDataAccessException {
        freeBoardRepository.deleteById(id);
    }

    /**
     * 게시판정보 삽입
     *
     * @param form 삽입할 게시판의 정보
     * @return
     */
    public FreeBoardResponse insertBoard(FreeBoardForm form) throws IllegalArgumentException, NoSuchElementException {
        Student studNo = Student.builder().studNo(form.getWriterNo()).name("temp").email("temp").department("temp").build();
        FreeBoard freeBoard = FreeBoard.builder()
                .student(studNo)
                .department(form.getDepartment())
                .title(form.getTitle())
                .content(form.getContent())
                .time(currentTime()).build();
        return new FreeBoardResponse(freeBoardRepository.save(freeBoard));
    }


    /**
     * 시간함수
     *
     * @return 현재시간
     */
    public String currentTime() {
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }

    /**
     * 학번전체를 받아 3,4번째 자리만 추출
     *
     * @param no 학번(2016xxxxx)
     * @return (ex - 16)
     */
    public Long studentNo(Long no) {
        Long temp = no / 100000;
        return temp - 2000;
    }

    public List<FreeBoardListResponse> getRecentBoard(Long id) {
        List<FreeBoard> result = freeBoardRepository.findByIdIsGreaterThanOrderByTimeDesc(id);
        return result.stream().map(e-> new FreeBoardListResponse(e, e.getComments(), studentNo(e.getStudent().getStudNo())+" "+e.getDepartment())).toList();
    }

    public List<FreeBoardListResponse> loadBoardAfterId(Long id) {
        List<FreeBoard> result = freeBoardRepository.findByIdIsLessThanOrderByTimeDesc(id, Pageable.ofSize(20));
        return result.stream().map(e-> new FreeBoardListResponse(e, e.getComments(), studentNo(e.getStudent().getStudNo())+" "+e.getDepartment())).toList();
    }

    public List<FreeBoardListResponse> searchBoard(String keyword) {
        List<FreeBoard> result = freeBoardRepository.findByKeywordOrderByTimeDesc(keyword, Pageable.ofSize(20));
        return result.stream().map(e-> new FreeBoardListResponse(e, e.getComments(), studentNo(e.getStudent().getStudNo())+" "+e.getDepartment())).toList();
    }

    public List<FreeBoardListResponse> searchMoreBoard(String keyword, long id) {
        List<FreeBoard> result = freeBoardRepository.findByKeywordAndIdLessThanOrderByTimeDesc(keyword, id, Pageable.ofSize(20));
        return result.stream().map(e-> new FreeBoardListResponse(e, e.getComments(), studentNo(e.getStudent().getStudNo())+" "+e.getDepartment())).toList();
    }

    public List<FreeBoardListResponse> previewFreeBoard() {
        List<FreeBoard> result = freeBoardRepository.findByOrderByTimeDesc(Pageable.ofSize(3));
        return result.stream().map(e-> new FreeBoardListResponse(e, e.getComments(), studentNo(e.getStudent().getStudNo())+" "+e.getDepartment())).toList();
    }
}
