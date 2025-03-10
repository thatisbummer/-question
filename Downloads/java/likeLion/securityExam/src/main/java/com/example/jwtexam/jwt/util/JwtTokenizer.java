package com.example.jwtexam.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenizer {
    private final byte[] accessSecret;
    private final byte[] refreshSecret;

    public static final Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; // 유지시간 30분
    public static final Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 10000L; // 유지시간 7일

    public JwtTokenizer(@Value("${jwt.secretKey}") String accessSecret,
                        @Value("${jwt.refreshKey}") String refreshSecret) {
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
    }

    // 액세스 토큰이나 리프레시 토큰을 만들때 사용할예정
    private String createToken(Long id, String email, String name, String username, List<String> roles,
                               Long expire, byte[] secretKey) {
        Claims claims = Jwts.claims().setSubject(email); // 겹치지 않는값 이걸로 정보를 찾는거임 대표 정보 느낌 이런걸 서브젝트에 넣음
        // 여기서 claims가 Payload에 해당해요. , 최종적으로 Jwts.builder().setClaims(claims)로 이 데이터를 토큰에 넣습니다.
        // 필요한 정보들을 저장
        claims.put("username", username);
        claims.put("name", name);
        claims.put("userId", id);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date()) // 토큰 발급시간을 알려주는것
                .setExpiration(new Date(new Date().getTime() + expire))  // expire 1000 * 60 * 60 1hour 토큰의 유통기한
                .signWith(getSigningKey(secretKey)) //비밀 키로 서명
                .compact(); //최종 JWT 문자열 생성 (예: header.payload.signature).

    }

    // 서명 키 생성 메서드
    //이 메서드는 비밀 키(secretKey, byte[] 타입)를 받아서 JWT 서명에 사용할 수 있는 Key 객체를 생성해요.
    private static Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
        // secretKey는 우리가 만든 비밀 문자열(예: "my-secret-key")을 바이트 배열로 변환한 거예요.
        // Keys.hmacShaKeyFor는 그 바이트 배열을 암호화에 적합한 형태(Key 객체)로 바꿔주는 도구예요. 마치 비밀 코드를 암호 상자에 맞게 가공하는 과정이라고 생각하면 됩니다.

    }

    // ACCESS Token 생성 메서드
    public String createAccessToken(Long id, String email,String name, String username, List<String> roles) {
        return createToken(id,email,name,username,roles,ACCESS_TOKEN_EXPIRE_COUNT,accessSecret);
    }

    // REFRESH Token 생성 메서드
    public String createRefreshToken(Long id, String email,String name, String username, List<String> roles) {
        return createToken(id,email,name,username,roles,REFRESH_TOKEN_EXPIRE_COUNT,refreshSecret);
    }

    // 파싱 : 데이터를 해석해서 원하는 형태로 변환하는 과정이에요. 쉽게 말하면, 복잡한 데이터를 쪼개서 이해하기 쉬운 정보로 만드는 작업입니다.
    // 주어진 토큰을 파싱해서 Claims(페이로드 정보)를 추출. 즉 들어온 토큰이 시크릿키로 해석해서  맞는지 확인하는 메서드 아래에서 사용할 예정
    public Claims parseToken(String token, byte[] secretKey) {
        return Jwts.parserBuilder() //JWT를 파싱할 수 있는 파서(Parser)를 만들기 시작합니다. 이건 io.jsonwebtoken 라이브러리의 도구예요.
                .setSigningKey(getSigningKey(secretKey))//파서에 서명 검증에 사용할 비밀 키(secretKey)를 설정합니다. getSigningKey는 secretKey를 Key 객체로 변환해서 반환하죠 이 키는 토큰을 만들 때 사용된 키와 같아야 해요. 그렇지 않으면 서명 검증이 실패합니다.
                .build()
                .parseClaimsJws(token)
                //실제로 token을 파싱합니다.
                //parseClaimsJws는 토큰의 서명(Signature)을 검증하고, 유효하면 내부 데이터를 해석해요.
                //서명이 맞지 않거나 토큰이 만료되었다면 예외를 던집니다(예: SignatureException, ExpiredJwtException).
                .getBody(); //파싱된 결과에서 Claims(페이로드 부분)를 추출해서 반환합니다.
//                            Claims는 subject, userId, roles 같은 정보를 담고 있어요.
    }

    public Claims parseAccessToken(String accessToken){
        return parseToken(accessToken,accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

    public Long getUserIdFromToken(String token) {
        // Bearer
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("JWT 토큰이 없습니다");
        }
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("유효하지 않은 형식입니다.");
        }
        Claims claims = parseToken(token, accessSecret);
        if (claims == null) {
            throw new IllegalArgumentException("유효하지 않은 형식입니다.");
        }
        Object userId = claims.get("userId");
        if (userId instanceof Number) {
            return ((Number) userId).longValue();
        } else {
            throw new IllegalArgumentException("JWT 토큰에서 userId를 찾을 수 없습니다.");
        }


    }

}
