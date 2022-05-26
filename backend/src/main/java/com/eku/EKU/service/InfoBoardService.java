package com.eku.EKU.service;


import com.eku.EKU.domain.InfoBoard;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.BoardListForm;
import com.eku.EKU.form.BoardListResponse;
import com.eku.EKU.form.InfoBoardForm;
import com.eku.EKU.form.InfoBoardResponse;
import com.eku.EKU.repository.InfoBoardRepository;
import com.eku.EKU.repository.StudentRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.eku.EKU.utils.RelativeTimeConverter.convertToRelativeTime;

@Service
public class InfoBoardService {
    public final InfoBoardRepository infoBoardRepository;
    private final StudentRepository studentRepository;

    public InfoBoardService(InfoBoardRepository infoBoardRepository, StudentRepository studentRepository) {
        this.infoBoardRepository = infoBoardRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * 게시물 불러오기
     * @param form
     * @return
     */
    public InfoBoard loadBoard(InfoBoardForm form)throws IllegalArgumentException, NoSuchElementException {
        InfoBoard board = infoBoardRepository.findById(form.getId()).get();
        board.setView(board.getView() + 1);
        infoBoardRepository.save(board);
        return board;
    }
    /**
     * 게시물 목록
     * @param listForm 해당 강의동 + page번호
     * @return List<InfoBoard>로 목록을 반환
     */
    public List<BoardListResponse> boardList(BoardListForm listForm)throws IllegalArgumentException, NoSuchElementException{
        Page<InfoBoard> list = infoBoardRepository.findAllByBuildingOrderByWrittenTime(listForm.getLectureBuilding(), Pageable.ofSize(20));
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
        if(!isCorrectBuilding(form.getBuilding()))
            throw new IllegalArgumentException();
        Student student = studentRepository.findById(form.getWriterNo()).orElseThrow();
        InfoBoard infoBoard = InfoBoard.builder()
                .no(student)
                .name(student.getName())
                .department(student.getDepartment())
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
        if(!isCorrectBuilding(form.getBuilding()))
            throw new IllegalArgumentException();
        InfoBoard board = infoBoardRepository.findById(form.getId()).get();
        if(!form.getTitle().isEmpty()&&!form.getContent().isEmpty()) {
            board.setContent(form.getContent());
            board.setTitle(form.getTitle());
            board.setBuilding(form.getBuilding());
            infoBoardRepository.save(board);
        }
    }

    public List<BoardListResponse> getRecentBoard(int building, long id) {
        List<InfoBoard> result = infoBoardRepository.findByBuildingAndIdIsGreaterThanOrderByWrittenTimeDesc(building, id);
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
     * building 코드가 맞는지 검사
     */
    public boolean isCorrectBuilding(String code) {
        if(code.length()!=10)
            return false;
        for(int i=0;i<code.length();i++){
            if(!code.substring(i,i+1).equals("0")&&!code.substring(i,i+1).equals("1"))
                return false;
        }
        return true;
    }

    public List<BoardListResponse> loadBoardAfterId(int building, Long id) {
        List<InfoBoard> result = infoBoardRepository.findByBuildingAndIdIsLessThanOrderByWrittenTimeDesc(building, id, Pageable.ofSize(20));
        return result.stream().map(BoardListResponse::new)
                .toList();
    }

    public List<BoardListResponse> searchBoard(int building, String keyword) {
        List<InfoBoard> result = infoBoardRepository.findByBuildingAndKeywordOrderByWrittenTimeDesc(building, keyword, Pageable.ofSize(20));
        return result.stream().map(BoardListResponse::new).toList();
    }

    public List<BoardListResponse> searchMoreBoard(int building, String keyword, long id) {
        List<InfoBoard> result = infoBoardRepository.findByBuildingAndKeywordAndIdLessThanOrderByWrittenTimeDesc(building, keyword, id, Pageable.ofSize(20));
        return result.stream().map(BoardListResponse::new).toList();
    }

    public List<BoardListResponse> previewInfoBoard(int building) {
        Page<InfoBoard> result = infoBoardRepository.findAllByBuildingOrderByWrittenTime(building, Pageable.ofSize(3));
        return result.stream().map(BoardListResponse::new).toList();
    }
}
