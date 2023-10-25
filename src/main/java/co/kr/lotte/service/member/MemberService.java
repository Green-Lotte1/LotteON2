package co.kr.lotte.service.member;


import co.kr.lotte.dto.member.EmailMessageDTO;
import co.kr.lotte.dto.member.MemberDTO;
import co.kr.lotte.entity.member.MemberTermsEntity;
import co.kr.lotte.entity.member.MemberEntity;
import co.kr.lotte.repository.member.TermsRepository;
import co.kr.lotte.repository.member.MemberRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@Service
public class MemberService {

    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    private static String generatedCode; // 인증코드 생성 시 사용할 변수

    public MemberTermsEntity findByTerms(){
        return termsRepository.findById(1).get();
    }

    public void save(MemberDTO dto){
        dto.setPass1(passwordEncoder.encode(dto.getPass1())); // 비밀번호 암호화
        MemberEntity entity = dto.toEntity(); // DTO를 Entity로 변환
        memberRepository.save(entity); // DB insert
    }
    public int countUidAndPass(String uid, String inputPass) {
        log.info("=========inputPass========="+inputPass);
        String encodePass = passwordEncoder.encode(inputPass);
        log.info("=========encodePass========="+encodePass);
        return memberRepository.countByUidAndPass(uid, encodePass);
    }

    public int countUid(String uid){
        return memberRepository.countByUid(uid);
    }
    public int countEmail(String email) {
        return memberRepository.countByEmail(email);
    }
    public MemberDTO findAllByEmail(String email) {
        return memberRepository.findAllByEmail(email).toDTO();
    }
    public int countNameAndEmail(String name, String email) {
        return memberRepository.countByNameAndEmail(name, email);
    }
    public int countUidAndEmail(String uid, String email) {
        return memberRepository.countByUidAndEmail(uid, email);
    }
    public int countHp(String hp) {
        return memberRepository.countByHp(hp);
    }
    public int countCompany(String company) {
        return memberRepository.countByCompany(company);
    }
    public int countBizRegNum(String bizRegNum) {
        return memberRepository.countByBizRegNum(bizRegNum);
    }
    public int countComRegNum(String comRegNum) {
        return memberRepository.countByComRegNum(comRegNum);
    }
    public int countTel(String tel) {
        return memberRepository.countByTel(tel);
    }
    public int countFax(String fax) {
        return memberRepository.countByFax(fax);
    }

    public int sendCodeByEmail(EmailMessageDTO emailMessage) {

        // 인증코드 생성
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        generatedCode = String.valueOf(code);

        // 메일 발송
        int status = 0;
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setFrom(new InternetAddress("LotteON@lotteOn.com", "LotteON", "UTF-8"));
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText("<h1>인증코드는 "+code+" 입니다.</h1>", true); // 메일 내용
            javaMailSender.send(mimeMessage);

            status = 1; // 메일 발송 시 status 1
            log.info("발송한 인증코드 : " + generatedCode);
            log.info("==================== 메일 전송 완료 ====================");

        }catch (Exception e) {
            log.info("******************** 메일 전송 실패 ********************");
            throw new RuntimeException(e);
        }

        return status;
    }
    public int confirmCodeByEmail(String code) {

        if (code.equals(generatedCode)) {
            log.info("+++ 인증번호 일치 +++");
            return 1;
        }else {
            log.info("--- 불 일 치 ---");
            return 0;
        }
    }

    // 비밀번호 찾기를 통한 비밀번호 재설정
    public void updatePass(String uid, String pass) {
        pass = passwordEncoder.encode(pass);
        MemberEntity entity = memberRepository.findById(uid).get();
        entity.setPass(pass);
        memberRepository.save(entity);
    }
}
