package com.example.securityExam.securityexam3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    public String admin() {
        return "abc";
    }
        @GetMapping("/a")
        public String admin1() {
            return "abcd";
        }
    @GetMapping("/b")
    public String admin2() {
        return "ab";
    }
    @GetMapping("/c")
    public String admin3() {
        return "bb";
    }

}
