package com.example.jwtexam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 리프레쉬 토큰을 데이터베이스에 저장하기 위한 엔티티
@Entity
@Getter
@Setter
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String value;
}
