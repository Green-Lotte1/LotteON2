package co.kr.lotte.controller.my;

import co.kr.lotte.dto.my.PageResponseDTO;
import co.kr.lotte.dto.my.SearchDTO;
import co.kr.lotte.dto.product.ProductOrderDTO;
import co.kr.lotte.entity.member.MemberEntity;
import co.kr.lotte.security.MyUserDetails;
import co.kr.lotte.service.my.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyController {
    @Autowired
    private MyService myService;

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

    @GetMapping("/my/orderList")
    @ResponseBody
    public PageResponseDTO orderList(SearchDTO searchDTO, @AuthenticationPrincipal Object principal) {
        MemberEntity memberEntity = ((MyUserDetails) principal).getMember();
        String uid = memberEntity.getUid();
        return myService.findOrderList(uid, searchDTO);
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