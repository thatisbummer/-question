package com.example.securityExam.securityexam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1.기본 설정
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated() // 모든 요청에 대해서 인증을 요구하겠다
//                )//.build();
//                .formLogin(Customizer.withDefaults());


        //  2.추가로 원하는 페이지는 인증없이 접근가능. 폼 로그인 인증 방식을 사용자가 원하는 설정으로..
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/hello", "/loginForm","/fail","test/*").permitAll() // 인증없이 접근 가능
                        .anyRequest().authenticated() // 모든 요청에 대해서 인증을 요구하겠다
                )//.build();
                .formLogin(formLogin -> formLogin
                       // .loginPage("/loginForm") //원하는 로그인 페이지 설정
                        .defaultSuccessUrl("/success")  //인증에 성공하면 가고싶은 페이지설정
                        .failureUrl("/fail") // 인증에 실패하면 넘어가는 페이지
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login_proc") // 기본 login  로그인 로직 Post방식임 데이터를 넘기는놈
                        .successHandler((request, response, authentication) -> {
                                log.info("로그인 성공!!" + authentication.getName());
                                response.sendRedirect("/info");
                            })
                        .failureHandler(((request, response, exception) -> {
                            log.info("로그인 실패::" + exception.getMessage());
                            response.sendRedirect("/login");

                        }))
                );


        // 로그아웃
        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/hello")
                        // 위의 설정으로 로그아웃이 가능핮미나.. 추가로 하고싶은 일이있다면..
                        .addLogoutHandler((request, response, authentication) -> {
                            // 추가로 해야할일이 뭐 있을까?
                            log.info("로그아 세션, 쿠키 삭제...");
                            HttpSession session = request.getSession();
                            if (session != null) {
                                session.invalidate(); // 세션 삭제
                            }
                        })
                        .deleteCookies("JSESSIONID") // 로그아웃시에 원하는 쿠키를 삭제할 수 있음
                );
        return http.build();
    }

}
