package com.eku.EKU.controller;

import com.eku.EKU.exceptions.NoSuchBuildingException;
import com.eku.EKU.exceptions.NoSuchDoodleException;
import com.eku.EKU.form.DoodleForm;
import com.eku.EKU.service.DoodleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class DoodleController {
    private final DoodleService doodleService;

    public DoodleController(DoodleService doodleService) {
        this.doodleService = doodleService;
    }

    @PostMapping("/doodle/write")
    public ResponseEntity<?> writeDoodle(@RequestBody DoodleForm form) {
        try {
            return ResponseEntity.ok(doodleService.applyDoodle(form));
        } catch (IllegalArgumentException | NoSuchBuildingException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/doodle/delete")
    public ResponseEntity<?> deleteDoodle(@RequestBody DoodleForm form) {
        try {
            doodleService.deleteDoodle(form);
            return ResponseEntity.ok(form.getDoodleId());
        } catch (NoSuchElementException | IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(form.getDoodleId());
        }
    }

    @PostMapping("/doodle/update")
    public ResponseEntity<?> updateDoodle(@RequestBody DoodleForm form) {
        try {
            doodleService.updateDoodle(form);
            return ResponseEntity.ok(form.getDoodleId());
        } catch (NoSuchDoodleException | IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(form.getDoodleId());
        }
    }

    @GetMapping("/doodle/read")
    public ResponseEntity<?> readDoodles(@RequestBody DoodleForm form) {
        try {
            return ResponseEntity.ok(doodleService.getRecentDoodle(form));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
