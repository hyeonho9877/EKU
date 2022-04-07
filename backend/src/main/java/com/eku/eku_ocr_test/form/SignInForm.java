package com.eku.eku_ocr_test.form;

import lombok.Data;

/**
 * 로그인 요청에 사용되는 Form 객체
 */
@Data
public class SignInForm {
    private String email;
    private String password;
}
