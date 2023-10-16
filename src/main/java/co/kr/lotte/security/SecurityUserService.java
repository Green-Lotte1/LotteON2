package co.kr.lotte.security;

import co.kr.lotte.entity.member.MemberEntity;
import co.kr.lotte.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService implements UserDetailsService {
	
	@Autowired
	private MemberRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 패스워드에 대한 검사는 이전 컴포넌트(AuthenticationProvider)에서 처리되어 사용자 아이디만 넘어옴		
		MemberEntity member = repo.findById(username)
				  			  .orElseThrow(()->new UsernameNotFoundException(username + " NotFound"));
		
		// 사용자 인증객체 생성(세션에 저장)
		UserDetails userDetails = MyUserDetails.builder()
									.member(member)
									.build();
		
		return userDetails;
	}

}
