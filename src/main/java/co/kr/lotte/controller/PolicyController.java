package co.kr.lotte.controller;

import co.kr.lotte.entity.member.MemberTermsEntity;
import co.kr.lotte.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 메인페이지 footer 약관용 컨트롤러
@Log4j2
@Controller
public class PolicyController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/policy/buyer")
    public String buyer(Model model, String success) {

        MemberTermsEntity terms = memberService.findByTerms();
        model.addAttribute("terms", terms);
        model.addAttribute("success", success);
        return "/policy/buyer";
    }

    @GetMapping("/policy/finance")
    public String finance(Model model, String success) {

        MemberTermsEntity terms = memberService.findByTerms();
        model.addAttribute("terms", terms);
        model.addAttribute("success", success);
        return "/policy/finance";
    }

    @GetMapping("/policy/privacy")
    public String privacy(Model model, String success) {

        MemberTermsEntity terms = memberService.findByTerms();
        model.addAttribute("terms", terms);
        model.addAttribute("success", success);
        return "/policy/privacy";
    }
}
