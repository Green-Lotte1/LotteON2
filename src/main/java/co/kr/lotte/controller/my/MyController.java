package co.kr.lotte.controller.my;

import co.kr.lotte.entity.member.MemberEntity;
import co.kr.lotte.security.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping(value = {"/my/", "/my/home"})
    public String home() {
        return "/my/home";
    }

    @GetMapping("/my/coupon")
    public String coupon() {
        return "/my/coupon";
    }

    @GetMapping("/my/info")
    public String info() {
        return "/my/info";
    }

    @GetMapping("/my/order")
    public String order(Model model, @AuthenticationPrincipal Object principal) {
        MemberEntity memberEntity = ((MyUserDetails) principal).getMember();
        String uid = memberEntity.getUid();
        return "/my/order";
    }

    @GetMapping("/my/point")
    public String point() {
        return "/my/point";
    }

    @GetMapping("/my/qna")
    public String qna() {
        return "/my/qna";
    }

    @GetMapping("/my/review")
    public String review() {
        return "/my/review";
    }
}