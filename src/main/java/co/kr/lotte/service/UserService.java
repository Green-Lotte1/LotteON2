package co.kr.lotte.service;


import co.kr.lotte.dto.MemberDTO;
import co.kr.lotte.entity.MemberTermsEntity;
import co.kr.lotte.entity.MemberEntity;
import co.kr.lotte.repository.TermsRepository;
import co.kr.lotte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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
        
        // 비밀번호 암호화
        dto.setPass1(passwordEncoder.encode(dto.getPass1()));

        // DTO를 Entity로 변환
        MemberEntity entity = dto.toEntity();

        // DB insert
        userRepository.save(entity);
    }

    public int countUid(String uid){
        return userRepository.countByUid(uid);
    }


}
