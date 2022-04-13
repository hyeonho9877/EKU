package com.eku.EKU.controller;

import com.eku.EKU.exceptions.NoSuchStudentException;
import com.eku.EKU.form.CriticForm;
import com.eku.EKU.service.CriticService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 강의평가에 대한 컨트롤러
 */
@RestController
public class CriticController {

    private final CriticService criticService;

    public CriticController(CriticService criticService) {
        this.criticService = criticService;
    }

    /**
     * 강의평가를 등록하는 메소드
     * @param form 등록하려는 강의평가의 정보
     * @return 성공 -> OK와 함께 저장에 성공한 강의평가 객체, 실패 -> BAD REQUEST
     */
    @PostMapping("/critic/apply")
    public ResponseEntity<?> applyCritic(@RequestBody CriticForm form) {
        try {
            System.out.println(form);
            return ResponseEntity.ok(criticService.applyCritic(form));
        } catch (NoSuchStudentException | IllegalStateException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(false);
        }
    }

    /**
     * 강의평가 삭제하는 메소드
     * @param form 삭제하려는 강의평가의 정보가 담긴 Form 객체
     * @return 성공 -> ok와 함께 삭제한 강의평가 번호, 실패 -> internal server error와 함께 삭제에 실패한 강의평가 번호
     */
    @PostMapping("/critic/rm")
    public ResponseEntity<?> removeCritic(@RequestBody CriticForm form){
        try {
            System.out.println(form);
            criticService.removeCritic(form.getCriticId());
            return ResponseEntity.ok(form.getCriticId());
        } catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getCriticId());
        }
    }

    /**
     * 강의평가 수정하는 메소드
     * @param form 수정하려는 강의 평가의 정보가 담긴 Form 객체
     * @return 성공 -> ok와 함께 수정에 성공한 강의 평가 번호, 실패 -> internal server error와 함께 삭제에 실패한 강의평가 번호
     */
    @PostMapping("/critic/update")
    public ResponseEntity<?> updateCritic(@RequestBody CriticForm form){
        try {
            criticService.updateCritic(form);
            return ResponseEntity.ok(form.getCriticId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getCriticId());
        }
    }

    /**
     * 강의평가 조회 메소드
     * @return 성공 -> ok와 함께 조회된 강의평가 리스트, 실패 -> bad request
     */
    @PostMapping("/critic/read")
    public ResponseEntity<?> readCritic(){
        try {
            return ResponseEntity.ok(criticService.readRecentCritic());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}