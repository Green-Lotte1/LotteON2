package co.kr.lotte.controller.member;

import groovy.util.logging.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class MemberController {

    @GetMapping("/member/login")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/member/join")
    public String join() {
        return "/member/join";
    }

    @GetMapping("/member/signup")
    public String signup(@RequestParam String type, Model model) {

        model.addAttribute("type", type);
        return "/member/signup";
    }

    @GetMapping("/member/register")
    public String register() {
        return "/member/register";
    }

    @GetMapping("member/registerSeller")
    public String registerSeller() {
        return "/member/registerSeller";
    }
}
