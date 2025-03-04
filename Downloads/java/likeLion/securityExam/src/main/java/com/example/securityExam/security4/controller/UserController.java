package com.example.securityExam.security4.controller;

import com.example.securityExam.security4.Service.UserService;
import com.example.securityExam.security4.domain.User;
import com.example.securityExam.security4.domain.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @GetMapping("/myinfo")
    public String myInfo(){
        return "exam4/myinfo";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "exam4/welcome";
    }

    @GetMapping("/")
    public String  home(){
        return "home";
    }

    //회원가입폼 요청
    @GetMapping("/signup")
    public String signup(){
        return "exam4/users/signup";
    }

    //회원가입요청
    @PostMapping("/userreg")
    public String userReg(@ModelAttribute User user){
        log.info("userreg++++++++++++++++++++++++++");

        //사용자가 입력한 username과 동일한 user가 이미 있는지??
        if(userService.existsUser(user.getUsername())){
            log.info("이미 사용중인 아이디 :: "+ user.getUsername());
            return "exam4/users/userreg-error";
        }


        userService.registUser(user);

        return "redirect:/loginform";
    }


    //로그인 폼 요청
    @GetMapping("/loginform")
    public String loginform(){

        return "exam4/users/loginform";
    }


    //회원가입요청
    @PostMapping("/userreg_role")
    public String userRegRole(@ModelAttribute UserRegisterDTO user){
        //사용자가 입력한 username과 동일한 user가 이미 있는지??
        if(userService.existsUser(user.getUsername())){
            log.info("이미 사용중인 아이디 :: "+ user.getUsername());
            return "exam4/users/userreg-error";
        }

        userService.registUser(user);

        return "redirect:/loginform";
    }
}
