package co.kr.lotte.controller.member;

import co.kr.lotte.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping("/member/check")
@RestController
public class MemberCheckController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/uid/{uid}")
    public int checkUid(@PathVariable("uid") String uid) {
        return memberService.countUid(uid);
    }
    @GetMapping("/email/{email}")
    public int checkEmail(@PathVariable("email") String email) {
        int result = memberService.countEmail(email);
        return result;
    }
    @GetMapping("/hp/{hp}")
    public int checkHp(@PathVariable("hp") String hp) {
        int result = memberService.countHp(hp);
        return result;
    }
    @GetMapping("/company/{company}")
    public int checkCompany(@PathVariable("company") String company) {
        int result = memberService.countCompany(company);
        return result;
    }
    @GetMapping("/bizRegNum/{bizRegNum}")
    public int checkBizRegNum(@PathVariable("bizRegNum") String bizRegNum) {
        int result = memberService.countBizRegNum(bizRegNum);
        return result;
    }
    @GetMapping("/comRegNum/{comRegNum}")
    public int checkComRegNum(@PathVariable("comRegNum") String comRegNum) {
        int result = memberService.countComRegNum(comRegNum);
        return result;
    }
    @GetMapping("/tel/{tel}")
    public int checkTel(@PathVariable("tel") String tel) {
        int result = memberService.countTel(tel);
        return result;
    }
    @GetMapping("/fax/{fax}")
    public int checkFax(@PathVariable("fax")String fax) {
        int result = memberService.countFax(fax);
        return result;
    }
}
