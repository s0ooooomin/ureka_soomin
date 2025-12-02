package com.mycom.myapp.user.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.entity.UserRole;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.repository.UserRoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	
	
	@Transactional // 원자성 (되면 다 되고, 안 되면 다 rollback)
	@Override 	// Parameter 원래는 UserDto로 받아서 User로 변경하는 작업 필요. 
				// BUT 오늘은 심플하게 걍 entity 바로 왔다갔다
	public UserResultDto insertUser(User user) {
		UserResultDto userResultDto = new UserResultDto();
		
		// 기존 UserRole 이름으로 find()
		// 사용자 회원가입 : 기본 IT 부여
		// 
//		List<UserRole> userRoles = List.of( userRoleRepository.findByName("IT") ); // user entity에서 이렇게 만들어놔서 1개여도 어쩔수X
//		user.setUserRoles(userRoles);
//		User savedUser = userRepository.save(user);
//		System.out.println(savedUser);
//		
		
//		Hibernate: select ur1_0.id,ur1_0.name from user_role ur1_0 where ur1_0.name=?
//		Hibernate: insert into user (email,name,password) values (?,?,?)
//		Hibernate: insert into user_user_roles (user_id,user_roles_id) values (?,?)
//		User(id=5, name=aaa, email=aaa, password=aaa)

		// #2. 새로운 UserRole 추가
		// 사용자 회원가입 시 새로운 role "test_mode" 부여
		// UserRole 객체가 생로 생설 -> 영속화X 상태에서 User 객체와 연결. User 객체만 save
//		UserRole userRole = new UserRole();
//		userRole.setName("test_role");
//		List<UserRole> userRoles = List.of( userRole ); // user entity에서 이렇게 만들어놔서 1개여도 어쩔수X
//		user.setUserRoles(userRoles);
//		User savedUser = userRepository.save(user);
//		System.out.println(savedUser);
		
		// #3. 새로운 UserRole 추가 + userRole save()
		// 사용자 회원가입 시 새로운 role "test_mode" 부여
		// UserRole 객체가 생로 생설 -> 영속화X 상태에서 User 객체와 연결. User 객체만 save
		// user_role → user → user_user_roles 순서대로 insert
//		UserRole userRole = new UserRole();
//		userRole.setName("test_role");
//		List<UserRole> userRoles = List.of( userRole ); // user entity에서 이렇게 만들어놔서 1개여도 어쩔수X
//		user.setUserRoles(userRoles);
//		userRoleRepository.save(userRole);
//		User savedUser = userRepository.save(user);
//		System.out.println(savedUser);

		// #4. 새로운 UserRole 추가 + User persist 설정
		// 사용자 회원가입 시 새로운 role "test_mode" 부여
		// UserRole 객체가 생로 생설 -> 영속화X 상태에서 User 객체와 연결. User 객체만 save
		// user -> user_role -> user_user_role
//		UserRole userRole = new UserRole();
//		userRole.setName("test_role");
//		List<UserRole> userRoles = List.of( userRole ); // user entity에서 이렇게 만들어놔서 1개여도 어쩔수X
//		user.setUserRoles(userRoles);
////		userRoleRepository.save(userRole);
//		User savedUser = userRepository.save(user);
//		System.out.println(savedUser);
		

		
		// ---------------RunTime 예외 발생 상황 ---------/
//		// #3-2. 새로운 UserRole 추가 + User persist 설정X + userRole save()
//		//userRole save() + NullPointerExeption 발생코드 + user save()
//		// => user_role만 insert. 나머지는 error로 처리 X
//		
//		UserRole userRole = new UserRole();
//		userRole.setName("test_role");
//		List<UserRole> userRoles = List.of( userRole ); // user entity에서 이렇게 만들어놔서 1개여도 어쩔수X
//		user.setUserRoles(userRoles);
//		userRoleRepository.save(userRole);
//
//		// nullPointerExption
//		String s = null;
//		s.length();
//		
//		User savedUser = userRepository.save(user);
//		System.out.println(savedUser);

		// #3-2-2. 3-2와 동일 + @Transactional
		// => user_role에도 들어가지 X (모두 rollback)
//		UserRole userRole = new UserRole();
//		userRole.setName("test_role");
//		List<UserRole> userRoles = List.of( userRole ); // user entity에서 이렇게 만들어놔서 1개여도 어쩔수X
//		user.setUserRoles(userRoles);
//		userRoleRepository.save(userRole);
//		
//		// nullPointerExption
//		String s = null;
//		s.length();
//		
//		User savedUser = userRepository.save(user);
//		System.out.println(savedUser);
		
// ------------중간결과-----------------//
		// @Transactional 을 통해 여러개의 repository 를 하나의 단위로 처리 가능
		// BUT! 응답 약속인 ~ResultDto 생성 문제 발생! -> try-catch ?
		try {
			// #3-2-2. 3-2와 동일 + @Transactional
			// => user_role에도 들어가지 X (모두 rollback)
			
			UserRole userRole = new UserRole();
			userRole.setName("test_role");
			List<UserRole> userRoles = List.of( userRole ); // user entity에서 이렇게 만들어놔서 1개여도 어쩔수X
			user.setUserRoles(userRoles);
			userRoleRepository.save(userRole);
			
			// nullPointerExption
//			String s = null;
//			s.length();
			
			User savedUser = userRepository.save(user);
			System.out.println(savedUser);
			
			userResultDto.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			userResultDto.setResult("fail");
		}
		
//		userResultDto.setResult("success");
		return userResultDto;
	}

}
