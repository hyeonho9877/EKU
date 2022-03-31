package com.eku.eku_ocr_test.controller;

import com.eku.eku_ocr_test.domain.Member;
import com.eku.eku_ocr_test.form.OcrForm;
import com.eku.eku_ocr_test.form.SignUpForm;
import com.eku.eku_ocr_test.service.SignUpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/signUp")
    public boolean signUp(@RequestBody SignUpForm form) {
        Member newMember = Member.builder()
                .studNo(form.getStudNo())
                .name(form.getName())
                .department(form.getDepartment())
                .email(form.getEmail())
                .build();

        return signUpService.enrollMember(newMember);
    }

    @GetMapping("/ocr")
    public Object ocr(@RequestBody OcrForm form){
        return signUpService.ocrImage(form);
    }
}
