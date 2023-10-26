package co.kr.lotte.controller.member;

import co.kr.lotte.dto.member.MemberDTO;
import co.kr.lotte.entity.member.MemberTermsEntity;
import co.kr.lotte.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member/login")
    public String login(Model model, String success) {
        model.addAttribute("success", success);
        return "/member/login";
    }

    @GetMapping("/member/join")
    public String join() {
        return "/member/join";
    }

    @GetMapping("/member/signup")
    public String signup(Model model, @RequestParam String type) {
        log.info("signup---type : " + type);

        model.addAttribute("type", type);
        MemberTermsEntity terms = memberService.findByTerms();
        model.addAttribute("terms", terms);
        return "/member/signup";
    }

    @GetMapping("/member/register")
    public String register(Model model, String type) {
        log.info("일반회원가입 type체크 : "+type);
        
        model.addAttribute("type", type);
        if (type.equals("seller"))
            return "redirect:/member/registerSeller?type=seller";
        return "/member/register";
    }
    @PostMapping("/member/register")
    public String register(MemberDTO dto, HttpServletRequest request) {
        
        dto.setRegip(request.getRemoteAddr());
        memberService.save(dto);
        return "redirect:/member/login?success=200";
    }

    @GetMapping("/member/registerSeller")
    public String registerSeller(Model model, String type) {
        log.info("판매자회원가입 type체크 : "+type);

        model.addAttribute("type", type);
        return "/member/registerSeller";
    }
    @PostMapping("/member/registerSeller")
    public String registerSeller(MemberDTO dto, HttpServletRequest request) {

        dto.setRegip(request.getRemoteAddr());
        dto.setLevel(5); // 판매자이므로
        dto.setName(dto.getCompany()); // 사용자 이름은 회사명으로
        memberService.save(dto);
        return "redirect:/member/login?success=200";
    }

    @GetMapping("/member/findId")
    public String findId() {
        return "/member/findId";
    }
    @GetMapping("/member/findIdResult")
    public String findIdResult() {
        return "/member/findIdResult";
    }
    @PostMapping("/member/findIdResult")
    public String findIdResult(Model model, String email) {

        MemberDTO dto = memberService.findAllByEmail(email);
        model.addAttribute("dto", dto);
        return "/member/findIdResult";
    }

    @GetMapping("/member/findPass")
    public String findPass() {
        return "/member/findPass";
    }
    @GetMapping("/member/findPassChange")
    public String findPassChange() {
        return "/member/findPassChange";
    }
    @PostMapping("/member/findPassChange")
    public String findPassChange(Model model, String email) {

        MemberDTO dto = memberService.findAllByEmail(email);
        model.addAttribute("dto", dto);
        return "/member/findPassChange";
    }
    @PostMapping("/member/findPassChangeDo")
    public String findPassChangeDo(MemberDTO dto) {
        memberService.updatePass(dto.getUid(), dto.getPass1());
        return "redirect:/member/login?success=201";
    }
}
