package co.kr.lotte.controller.user;

import co.kr.lotte.dto.MemberDTO;
import co.kr.lotte.entity.MemberTermsEntity;
import co.kr.lotte.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){
        MemberTermsEntity terms  = userService.findByTerms();
        model.addAttribute(terms);
        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register(){
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String register(MemberDTO dto){


        userService.save(dto);
        return "redirect:/user/login";
    }


}
