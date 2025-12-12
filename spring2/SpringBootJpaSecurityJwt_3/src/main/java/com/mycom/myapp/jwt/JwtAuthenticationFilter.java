package com.mycom.myapp.jwt;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mycom.myapp.config.MyUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
모든 요청마다 실행되면서
	헤더에서 JWT 가져오고
	토큰 유효성 검사
	토큰에서 username / roles 꺼내서
	SecurityContextHolder 에 Authentication 을 채워 넣는 역할.
	즉,
	“JWT → Spring Security 인증 객체로 바꿔서 뒤에 오는 필터/컨트롤러가 @AuthenticationPrincipal 같은 걸 쓸 수 있게 해주는 필터” 라고 보면 됨.
*/

// client 가 전송한 token 유효성 검증
// OncePerRequestFilter 는 request 하나 당 추가적인 forwarding request 발생에 대해 한 번만 수행되도록 보장
// 어떤 request 에 대해 ...forward() 하면 request 가 새로 발생하게 되고 filter 가 중복 적용된다.

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    

    @Override
    protected void doFilterInternal(
    	HttpServletRequest servletRequest,
        HttpServletResponse servletResponse,
        FilterChain filterChain
	) throws ServletException, IOException {
    	
    	String token = jwtUtil.getTokenFromHeader(servletRequest);

        // 1차 토큰 검증 + Claims 파싱을 한 번에 처리
        Claims claims = null;
        if (token != null) {
            claims = jwtUtil.validateToken(token); // 유효하면 Claims, 아니면 null
        }

        System.out.println(claims);
        
        if (claims != null) {
        	
        	// 우리의 토큰 검증 로직은 여기까지
        	System.out.println("토큰 검증 완료");
        	
			// Spring Security 가 Client request 에 대한 기본 검증을 이어가도록 처리 
			// 토큰으로부터 username, roles 을 얻어서 이후 filter chaing 을 이어 나가야 함.        	
        	String username = claims.getSubject(); // token 생성시 subject 에 userId 저장
        	
        	System.out.println("username");
        	System.out.println(username);
        	
        	List<?> roles = (List<?>) claims.get("roles"); // List<String> 으로 token 에 넣었지만 꺼낼 때는 ? 으로 우선 List 를 만든다.
        	
        	// String role 을 SimpleGrantedAuthority 로
        	// 아래 UsernamePasswordAuthenticationToken 객체 생성자에 전달.
        	// 
        	List<SimpleGrantedAuthority> authorities = roles.stream()
        			.map(roleName -> (String) roleName)  // <?> -> <String> 
                    .map(SimpleGrantedAuthority::new) 
                    .toList();
        	
        	System.out.println("authorities");
        	System.out.println(authorities);
        	
        	// 아래 2개 비교할 때 console 의 로그 확인
        	// #1. 1차 검증만 진행
//        	UsernamePasswordAuthenticationToken authenticationToken 
//        			= new UsernamePasswordAuthenticationToken(username, null, authorities);
        	
        	
        	// #2. 2차 검증 ( DB Access 추가 ) 까지 한다면
        	//     사용자 요청마다 DB Access 필요
        	UsernamePasswordAuthenticationToken authenticationToken = jwtUtil.getAuthentication(token);
        	
        	// 현재 Filter 를 처리하는 Thread 의 Security Context 에 저장해서 이어지는 request lifecycle 에서 authenticationToken 이 유효하도록 한다.
        	// 아래 코드가 없으면 security context 가 empty 가 되어 이어지는 request lifecycle 에 대해 unauthenticated 로 간주
        	// 401(Unauthorized), 403(Forbidden)
        	authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);        	
        	         	
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}