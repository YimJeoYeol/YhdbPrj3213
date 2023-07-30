package com.zerobase.application.security.auth;

import com.zerobase.domain.RoleType;
import com.zerobase.domain.User;
import com.zerobase.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.zerobase.domain.RoleType.*;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private final UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findByUserid(userid);


		if(userEntity != null ) {
			return new PrincipalDetails(userEntity);
		}
		return null;
		//return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");

	
	}
}
