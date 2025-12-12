package com.mycom.myapp.auth.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.mycom.myapp.auth.dto.LoginResultDto;
import com.mycom.myapp.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j // log field inject
public class LoginServiceImpl implements LoginService {
	
	// Spring Security 를 통한 Login ( <= 단순 이메일 패스워드 비교 뿐 아니라, Security 관련 내부 처리가 함께 이루어 지닌 )
    private final AuthenticationManager authenticationManager;
    
    // Login 성공하면 jwt 발급
    private final JwtUtil jwtUtil;
	
	public LoginResultDto login(String email, String password) {
		
		LoginResultDto loginResultDto = new LoginResultDto();
		
		try {
			log.debug("userRepository.findByEmail(email)");
			
//			AuthenticationManager 는 내부적으로:
//				DaoAuthenticationProvider 에게 authenticate 위임
//				DaoAuthenticationProvider 는
//				→ UserDetailsService.loadUserByUsername(email) 호출
//				DB 사용자 조회
//				비밀번호 비교 passwordEncoder.matches(raw, encoded)
//				인증 성공하면 Authentication 객체 생성하여 반환
//				주의! 기존 세션 기반에서는 Security가 ROLE_ prefix 를 자동으로 붙였음, JWT 기반에서는 토큰에 우리가 직접 roles 를 넣어야 함.
			//		=> MyUserDetailService 의 loadUserByUsername() 에서 붙여준다. 

			// 이 한 줄이 Spring Security 전체 인증 절차를 자동으로 수행해줌
			Authentication authentication = authenticationManager.authenticate(
				// 파라미터 의미 => principal(email), credentials(password)
				new UsernamePasswordAuthenticationToken(email, password) 
			);
	    		
			String username = authentication.getName();
			List<String> roles = authentication.getAuthorities().stream()
	                    .map(GrantedAuthority::getAuthority).toList();
			
			String token = jwtUtil.createToken(username, roles);
			System.out.println(token);
			loginResultDto.setResult("success");
			loginResultDto.setToken(token);

        }catch(Exception e) {
        	loginResultDto.setResult("fail");
        }
        
        return loginResultDto;
    }
}
