package com.eku.eku_ocr_test.controller;

import com.eku.eku_ocr_test.callbacks.OcrCallBack;
import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.form.OcrForm;
import com.eku.eku_ocr_test.form.OcrResponseForm;
import com.eku.eku_ocr_test.form.SignUpForm;
import com.eku.eku_ocr_test.service.MailService;
import com.eku.eku_ocr_test.service.SignUpService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SignUpController {
    private final SignUpService signUpService;
    private final MailService mailService;

    public SignUpController(SignUpService signUpService, MailService mailService) {
        this.signUpService = signUpService;
        this.mailService = mailService;
    }

    @PostMapping("/signUp")
    public boolean signUp(@RequestBody SignUpForm form) {
        Student student = Student.builder()
                .studNo(form.getStudNo())
                .name(form.getName())
                .department(form.getDepartment())
                .email(form.getEmail())
                .authenticated(false)
                .build();
        if (mailService.validateEmail(form.getEmail())) {
            signUpService.enrollClient(student)
                    .ifPresent(c -> {
                        mailService.sendAuthMail(c, form.getEmail());
                    });
            return true;
        } else return false;
}

    @PostMapping("/signUp/ocr")
    public void ocr(@RequestBody OcrForm form, HttpServletResponse response) {
        try {
            signUpService.compCompsApiTest(
                    form,
                    new OcrCallBack() {
                        @Override
                        public void onSuccess(OcrResponseForm ocrResponseForm) throws IOException {
                            response.getWriter().write(signUpService.parseOcrResponse(ocrResponseForm));
                        }

                        @Override
                        public void onFailed() throws IOException {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    }
            );
        } catch (IOException e) {

        }
    }

    @GetMapping("/signUp/emailAuth")
    public boolean authIdentity(@RequestParam String key) {
        System.out.println("/signUp/emailAuth -> authKey: " + key);
        return signUpService.authEmail(key);
    }
}
