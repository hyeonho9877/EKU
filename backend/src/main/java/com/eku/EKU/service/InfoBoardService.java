package com.eku.EKU.service;


import com.eku.EKU.domain.InfoBoard;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.BoardListForm;
import com.eku.EKU.form.BoardListResponse;
import com.eku.EKU.form.InfoBoardForm;
import com.eku.EKU.form.InfoBoardResponse;
import com.eku.EKU.repository.InfoBoardRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.eku.EKU.utils.RelativeTimeConverter.convertToRelativeTime;

@Service
public class InfoBoardService {
    public final InfoBoardRepository infoBoardRepository;



    public InfoBoardService(InfoBoardRepository infoBoardRepository) {
        this.infoBoardRepository = infoBoardRepository;
    }

    /**
     * 게시물 불러오기
     * @param form
     * @return
     */
    public InfoBoard loadBoard(InfoBoardForm form)throws IllegalArgumentException, NoSuchElementException {
        InfoBoard board = infoBoardRepository.findById(form.getId()).get();
        return board;
    }
    /**
     * 게시물 목록
     * @param listForm 해당 강의동 + page번호
     * @return List<InfoBoard>로 목록을 반환
     */
    public List<BoardListResponse> boardList(BoardListForm listForm)throws IllegalArgumentException, NoSuchElementException{
        Pageable pageable = PageRequest.of(listForm.getPage(), 20);
        Page<InfoBoard> list = infoBoardRepository.findAllByBuildingOrderByWrittenTime(listForm.getLecture_building(), pageable);
        List<BoardListResponse> newList = new ArrayList<BoardListResponse>();
        for(InfoBoard i : list){
            String writer = i.getDepartment() + " " + i.getName();
            BoardListResponse form = BoardListResponse.builder()
                    .id(i.getId())
                    .title(i.getTitle())
                    .writer(writer)
                    .time(convertToRelativeTime(i.getWrittenTime()))
                    .view(i.getView())
                    .no(i.getNo().getStudNo())
                    .build();
            newList.add(form);
        }
        return newList;
    }
    /**
     * 게시판정보 삽입
     * @param form 삽입할 게시판의 정보
     * @return
     */
    public InfoBoardResponse insertBoard(InfoBoardForm form) throws IllegalArgumentException, NoSuchElementException {
        Student studNo = Student.builder().studNo(form.getWriterNo()).name("temp").email("temp").department("temp").build();
        InfoBoard infoBoard = InfoBoard.builder()
                .no(studNo)
                .name(form.getName())
                .department(form.getDepartment())
                .title(form.getTitle())
                .content(form.getContent())
                .writtenTime(currentTime())
                .building(form.getBuilding())
                .build();
        return new InfoBoardResponse(infoBoardRepository.save(infoBoard));
    }
    /**
     * 게시판정보 수정
     * @param form 수정할 게시판의 정보
     */
    public void updateBoard(InfoBoardForm form) throws IllegalArgumentException, NoSuchElementException{
        InfoBoard board = infoBoardRepository.findById(form.getId()).get();
        if(!form.getTitle().isEmpty()&&!form.getContent().isEmpty()) {
            board.setContent(form.getContent());
            board.setTitle(form.getTitle());
            board.setBuilding(form.getBuilding());
            infoBoardRepository.save(board);
        }
    }

    public List<BoardListResponse> getRecentBoard(long id) {
        List<InfoBoard> result = infoBoardRepository.findByIdIsGreaterThanOrderByWrittenTimeDesc(id);
        return result.stream().map(BoardListResponse::new)
                .toList();
    }
    /**
     * 게시물 삭제
     * @param id 해당 게시물 번호
     */
    public void deleteBoard(Long id) throws EmptyResultDataAccessException,IllegalArgumentException, NoSuchElementException {
        infoBoardRepository.deleteById(id);
    }

    /**
     * 시간함수
     * @return 현재시간
     */
    public String currentTime(){
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }
    /**
     * 1011001010 이런식으로 오는 강의동코드와 목록이 표시되어야할 강의동 번호가 일치하는지 검사하는 함수
     */
    public boolean isCorrectBuilding(String code, int no){

        if(no<1||no>10)
            return false;
        else if(code.substring(no-1,no).equals("1"))
            return true;
        return false;
    }

    public List<BoardListResponse> loadBoardAfterId(Long id) {
        List<InfoBoard> result = infoBoardRepository.findByIdIsLessThanOrderByWrittenTimeDesc(id, Pageable.ofSize(20));
        return result.stream().map(BoardListResponse::new)
                .toList();
    }
}
