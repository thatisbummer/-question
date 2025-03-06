package com.example.securityexam3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    public String user() {
        return "useraaa";
    }
    @GetMapping("/a")
    public String user2() {
        return "useraaa2";
    }
    @GetMapping("/b")
    public String user3() {
        return "useraaa3";
    }
}
