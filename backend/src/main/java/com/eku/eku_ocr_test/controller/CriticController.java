package com.eku.eku_ocr_test.controller;

import com.eku.eku_ocr_test.exceptions.NoSuchStudentException;
import com.eku.eku_ocr_test.form.CriticForm;
import com.eku.eku_ocr_test.service.CriticService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class CriticController {

    private final CriticService criticService;

    public CriticController(CriticService criticService) {
        this.criticService = criticService;
    }

    @PostMapping("/critic/apply")
    public ResponseEntity<?> applyCritic(@RequestBody CriticForm form) {
        try {
            System.out.println(form);
            return ResponseEntity.ok(criticService.applyCritic(form));
        } catch (NoSuchStudentException | NoSuchElementException | IllegalStateException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(false);
        }
    }

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
}