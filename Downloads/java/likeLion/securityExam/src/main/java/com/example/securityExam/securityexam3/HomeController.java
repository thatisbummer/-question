package com.example.securityExam.securityexam3;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping
    public String hello() {
        return "hell";
    }

    @GetMapping("/info")
    public String info() {
        String message = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            message = "로그인된 사용자가 없습니다.";
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {  // 사실 거의 이 if문만 필요함
            UserDetails userDetails = (UserDetails) principal;
            message = "현재 로그인 한 사용자 : " + userDetails.getUsername();
        } else {
            message = "현재 로그인 한 사용자 : " + principal.toString();
        }

        return message;
    }

    @GetMapping("/a")
    public String aaa() {
        // HelloService의 userLog()를 호출하면, 현재 로그인한 사용자 이름을 log.info로 출력 해주보자

        homeService.userLog();
        return "a";
    }

    @GetMapping("/b")
    public String b(@AuthenticationPrincipal UserDetails userDetails) {

        return "bbb " + userDetails.getUsername();
    }

    @GetMapping("/c")
    public String c() {
        return "cc";
    }
}
