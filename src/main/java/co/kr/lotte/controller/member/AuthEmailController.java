package co.kr.lotte.controller.member;

import co.kr.lotte.dto.member.EmailMessageDTO;
import co.kr.lotte.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/member/find")
public class AuthEmailController {

    @Autowired
    MemberService memberService;

    @GetMapping("/authEmail")
    public Map<String, Integer> authEmail(@RequestParam Map<String, Object> params) {

        // authEmail.js에서 AJAX로 전송한 데이터 수신
        String type  = (String) params.get("type");
        String uid   = (String) params.get("uid");
        String name  = (String) params.get("name");
        String email = (String) params.get("email");
        log.info("AuthEmailController type ---- "+type);
        log.info("AuthEmailController uid ---- "+uid);
        log.info("AuthEmailController name ---- "+name);
        log.info("AuthEmailController email ---- "+email);

        EmailMessageDTO emailMessageDTO = EmailMessageDTO.builder()
                                                        .to(email)
                                                        .subject("[LotteON] 이메일 인증 코드입니다.")
                                                        .build();

        int result = 0;
        int status = 0;

        if (Objects.equals(type, "normal") || Objects.equals(type, "seller")) // NullPointerException 방지
        {
            result = memberService.countEmail(email); // 중복여부(1 or 0)

            if (result == 0)
                status = memberService.sendCodeByEmail(emailMessageDTO); // 전송결과(1 or 0)

            log.info("AuthEmailController result ====== "+result);
            log.info("AuthEmailController status ====== "+status);
        }
        else if (Objects.equals(type, "FIND_ID"))
        {
            result = memberService.countNameAndEmail(name, email); // 중복여부(1 or 0)

            if (result == 1)
                status = memberService.sendCodeByEmail(emailMessageDTO); // 전송결과(1 or 0)

            log.info("AuthEmailController result ====== "+result);
            log.info("AuthEmailController status ====== "+status);
        }
        else if (Objects.equals(type, "FIND_PASS"))
        {
            result = memberService.countUidAndEmail(uid, email); // 중복여부(1 or 0)

            if (result == 1)
                status = memberService.sendCodeByEmail(emailMessageDTO); // 전송결과(1 or 0)

            log.info("AuthEmailController result ====== "+result);
            log.info("AuthEmailController status ====== "+status);
        }

        Map<String, Integer> returnMap = new HashMap<>();
        returnMap.put("result", result);
        returnMap.put("status", status);

        return returnMap;
    }

    @PostMapping("/authEmailCode")
    public int authEmailCode(@RequestParam Map<String, Object> params) {

        // 사용자가 입력한 코드 수신
        String code = (String) params.get("code");
        log.info("AuthEmailController 수신 코드 : "+code);

        // 인증코드 일치여부 체크
        int result = memberService.confirmCodeByEmail(code);
        return result;
    }
}
