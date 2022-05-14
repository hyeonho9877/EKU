package com.eku.EKU.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class HomeController {

    @GetMapping("/")
    public String test(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            System.out.println("ip : "+request.getRemoteAddr());
            System.out.println(header+" : "+request.getHeader(header));
        }
        return "access success";
    }

    @GetMapping("/http")
    public String httpTest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            System.out.println("ip : "+request.getRemoteAddr());
            System.out.println(header+" : "+request.getHeader(header));
        }
        return request.getRemoteAddr();
    }

    @GetMapping("/https")
    public String httpsTest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            System.out.println("ip : "+request.getRemoteAddr());
            System.out.println(header+" : "+request.getHeader(header));
        }
        return request.getRemoteAddr();
    }
}