package co.kr.lotte.controller.my;

import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.dto.my.MemberPointPageResponseDTO;
import co.kr.lotte.dto.my.PageResponseDTO;
import co.kr.lotte.dto.my.ReviewPageResponseDTO;
import co.kr.lotte.dto.my.SearchDTO;
import co.kr.lotte.entity.member.MemberEntity;
import co.kr.lotte.security.MyUserDetails;
import co.kr.lotte.service.CsCateService;
import co.kr.lotte.service.CsService;
import co.kr.lotte.service.my.MyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

@Log4j2
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

    @GetMapping("/my/infoAccessCheck")
    public String infoAccessCheck() {
        return "/my/infoAccessCheck";
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

    @GetMapping("/my/pointList")
    @ResponseBody
    public MemberPointPageResponseDTO pointList(SearchDTO searchDTO, @AuthenticationPrincipal Object principal) {
        MemberEntity memberEntity = ((MyUserDetails) principal).getMember();
        String uid = memberEntity.getUid();
        return myService.findPointList(uid, searchDTO);
    }

    @GetMapping("/my/reviewList")
    @ResponseBody
    public ReviewPageResponseDTO reviewList(SearchDTO searchDTO, @AuthenticationPrincipal Object principal) {
        MemberEntity memberEntity = ((MyUserDetails) principal).getMember();
        String uid = memberEntity.getUid();
        return myService.findReviewList(uid, searchDTO);
    }

    @GetMapping("/my/point")
    public String point() {
        return "/my/point";
    }

    @GetMapping("/my/qna")
    public String qna(Model model, @AuthenticationPrincipal Object principal, CsPageRequestDTO csPageRequestDTO) {
        MemberEntity memberEntity = ((MyUserDetails) principal).getMember();
        String uid = memberEntity.getUid();
        CsPageResponseDTO csPageResponseDTO = myService.findByUid(uid,csPageRequestDTO);

        log.info("myPageResponseDTO cate : "+ csPageRequestDTO.getCate());
        log.info("myPageResponseDTO pg : "+ csPageResponseDTO.getPg());
        log.info("myPageResponseDTO size : "+ csPageResponseDTO.getSize());
        log.info("myPageResponseDTO total : "+ csPageResponseDTO.getTotal());
        log.info("myPageResponseDTO start : "+ csPageResponseDTO.getStart());
        log.info("myPageResponseDTO end : "+ csPageResponseDTO.getEnd());
        log.info("myPageResponseDTO prev : "+ csPageResponseDTO.isPrev());
        log.info("myPageResponseDTO next : "+ csPageResponseDTO.isNext());
        model.addAttribute(csPageResponseDTO);
        return "/my/qna";
    }


    @GetMapping("/my/review")
    public String review() {
        return "/my/review";
    }
}