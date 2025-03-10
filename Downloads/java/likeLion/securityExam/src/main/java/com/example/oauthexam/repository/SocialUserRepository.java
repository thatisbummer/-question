package com.example.oauthexam.repository;

import com.example.oauthexam.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    Optional<SocialUser> findBySocialIdAndProvider(String socialId, String provider);
}
