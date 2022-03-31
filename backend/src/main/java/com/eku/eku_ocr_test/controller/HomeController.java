package com.eku.eku_ocr_test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @PostMapping("/")
    public String test() {
        return "access success";
    }
}