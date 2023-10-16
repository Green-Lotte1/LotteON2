package co.kr.lotte.service;


import co.kr.lotte.dto.member.MemberDTO;
import co.kr.lotte.entity.member.MemberTermsEntity;
import co.kr.lotte.entity.member.MemberEntity;
import co.kr.lotte.repository.member.TermsRepository;
import co.kr.lotte.repository.member.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public MemberTermsEntity findByTerms(){
        return termsRepository.findById(1).get();
    }

    public void save(MemberDTO dto){
        dto.setPass(passwordEncoder.encode(dto.getPass())); // 비밀번호 암호화
        MemberEntity entity = dto.toEntity(); // DTO를 Entity로 변환
        userRepository.save(entity); // DB insert
    }

    public int countUid(String uid){
        return userRepository.countByUid(uid);
    }


}
