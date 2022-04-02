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

/**
 * 회원가입 요청에 응답하는 컨트롤러
 */
@RestController
public class SignUpController {
    private final SignUpService signUpService;
    private final MailService mailService;

    public SignUpController(SignUpService signUpService, MailService mailService) {
        this.signUpService = signUpService;
        this.mailService = mailService;
    }

    /**
     * 회원 가입에 필요한 내용(이메일, 학번, 학과 등)을 완전히 입력한 후
     * 회원 가입 요청이 들어올 때 실행되는 메소드
     * @param form 사용자의 입력 내용 폼
     * @return DB에 성공적으로 데이터가 저장되고 이메일이 발송되었을 경우 True를 리턴하며, 그렇지 못할 경우 False를 리턴한다.
     */
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

    /**
     * 사용자가 학생증(모바일, 카드형) 이미지를 전송하여 OCR 서비스를 요청할 경우
     * 실행되는 메소드
     * @param form OCR 리퀘스트를 리다이렉트 할 시 필요한 폼
     * @param response 응답 바디에 내용을 쓰는데 필요한 HttpServletResponse 객체(스프링 컨테이너에 의해 자동 DI)
     */
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

    /**
     * 사용자가 서버에 의해 발송된 인증 메일의 링크를 클릭할 경우 수행되는 메소드
     * @param key get 방식으로 전송되는 파라미터로 사용자를 유일하게 식별할 수 있는 KEY
     * @return 인증이 성공적으로 수행될 경우 True를 리턴, 그렇지 못할 경우 False를 리턴
     */
    @GetMapping("/signUp/emailAuth")
    public boolean authIdentity(@RequestParam String key) {
        System.out.println("/signUp/emailAuth -> authKey: " + key);
        return signUpService.authEmail(key);
    }
}
