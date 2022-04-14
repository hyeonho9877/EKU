package com.eku.EKU.form;

import lombok.Data;

/**
 * 회원가입시에 사용되는 Form 객체
 */
@Data
public class SignUpForm {
    private Long studNo;
    private String password;
    private String name;
    private String department;
    private String email;
}
