package com.example.oauthexam.service;


import com.example.oauthexam.DTO.SocialUserRequestDTO;
import com.example.oauthexam.entity.User;
import com.example.oauthexam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 저장하는 메서드 한번들어오고 기억을못하면 안되니까
    @Transactional
    public User saveUser(SocialUserRequestDTO requestDTO) {
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setSocialId(requestDTO.getSocialId());
        user.setProvider(requestDTO.getProvider());
        user.setPassword(passwordEncoder.encode("")); // 소셜로그인으로 진행하는 사용자는 비밀번호를 비워둔다.

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByProviderAndSocialId(String provider, String socialId) {
        return userRepository.findByProviderAndSocialId(provider, socialId);
    }

}
