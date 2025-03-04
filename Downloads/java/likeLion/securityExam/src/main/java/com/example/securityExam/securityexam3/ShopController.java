package com.example.securityExam.securityexam3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShopController {
    public String shop() {
        return "product";
    }
    @GetMapping("/a")
    public String abc() {
        return "shoe";
    }
}
