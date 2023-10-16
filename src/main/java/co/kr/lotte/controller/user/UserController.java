package co.kr.lotte.controller.user;

import co.kr.lotte.dto.member.MemberDTO;
import co.kr.lotte.entity.member.MemberTermsEntity;
import co.kr.lotte.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/user/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){
        MemberTermsEntity terms  = memberService.findByTerms();
        model.addAttribute(terms);
        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register(){
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String register(MemberDTO dto){


        memberService.save(dto);
        return "redirect:/user/login";
    }


}
