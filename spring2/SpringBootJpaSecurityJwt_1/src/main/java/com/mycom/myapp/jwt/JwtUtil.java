package com.mycom.myapp.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

	// DB Access 를 위한 서비스
//	private final MyUserDetailsService myUserDetailsService;
	
    @Value("${myapp.jwt.secret}")
    private String secretKeyStr;
    private SecretKey secretKey;
    private final long tokenValidDuration = 1000L * 60 * 60 * 24; // 24시간 토큰 유효
//    private final long tokenValidDuration = 1000L * 60; // 1분 토큰 유효
    
    @PostConstruct
    protected void init() {
        System.out.println(secretKeyStr);
        
        secretKey = new SecretKeySpec(
        		secretKeyStr.getBytes(StandardCharsets.UTF_8), 
        		Jwts.SIG.HS256.key().build().getAlgorithm()
        	);
        System.out.println(secretKey);
    }

    // JWT 토큰 생성
    public String createToken(String username, List<String> roles) {
        
        Date now = new Date();

        String token = Jwts.builder()
        		.subject(username)   // subject 에 username
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidDuration))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();        
        			
        return token;
    }

    // token 에 담긴 username(subject) 으로, UserDetailsService.loadUserByUsername(username) 호출하고 
    // 이를 통해 최신의 UserDetails 릉 얻고,
    // 이를 통해 새로운 UsernamePasswordAuthenticationToken 객체를 반환한다.
    // JwtAuthenticationFilter 에서 token 검증 시 #2 DB Access 까지 할 경우 사용. 현재 코드에서는 사용 X
//    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
//    	
//        UserDetails userDetails = myUserDetailsService.loadUserByUsername(this.getUsernameFromToken(token));
//        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "",userDetails.getAuthorities());
//    }

    public String getUsernameFromToken(String token) {
      	
    	String subject = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token).getPayload()
                .getSubject();  

        return subject;
    }

//	  일반적으로 client 에서 Authorization: Bearer token 문자열 형식으로 보내는 방식
//    그에 대해 token 을 가져오는 부분도 아래 코드처럼 작성하는 경우 많다.
//    String authHeader = request.getHeader("Authorization");
//    if (authHeader != null && authHeader.startsWith("Bearer ")) {
//        String token = authHeader.substring(7);
//        ...
//    }
    
    public String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String token) {

    	// try-catch 가 없어도 동작하나, 로그인 하지 않은 상태에서 token 이 필요한 요청이 오면 io.jsonwebtoken.MalformedJwtException 등
    	// 예외 로그가 길게 남는다. 그 부분 귀찮으면 try - catch 적용
    	try {
        	return ! Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token).getPayload()
                    .getExpiration().before(new Date());  
    	}catch(Exception e) {
    		return false;
    	}
    }
}