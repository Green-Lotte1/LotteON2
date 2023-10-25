package co.kr.lotte.controller.my;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.dto.member.MemberCouponDTO;
import co.kr.lotte.dto.member.MemberPointDTO;
import co.kr.lotte.dto.my.MemberPointPageResponseDTO;
import co.kr.lotte.dto.my.PageResponseDTO;
import co.kr.lotte.dto.my.ReviewPageResponseDTO;
import co.kr.lotte.dto.my.SearchDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.dto.product.ProductOrderDTO;
import co.kr.lotte.dto.product.ProductOrderItemDTO;
import co.kr.lotte.dto.product.ProductReviewDTO;
import co.kr.lotte.entity.member.MemberEntity;
import co.kr.lotte.entity.member.MemberPointEntity;
import co.kr.lotte.entity.product.ProductOrderEntity;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class MyController {
    @Autowired
    private MyService myService;

    @GetMapping(value = {"/my/", "/my/home"})
    public String home(Model model, @AuthenticationPrincipal Object principal) {
        MemberEntity memberEntity = ((MyUserDetails) principal).getMember();
        String uid = memberEntity.getUid();
        List<ProductOrderItemDTO> score1List = myService.findTop1ByOrdUidOrderByOrdDateDesc(uid);
        List<BoardDTO> myQnas = myService.findTop5ByUidOrderByRdateDesc(uid);
        List<MemberPointDTO> pointList = myService.findPointByUid(uid);
        List<ProductReviewDTO> reviewList = myService.findReviewByUid(uid);
        log.info("score1List :" + score1List );
        log.info("myQnas :" + myQnas );
        log.info("pointList :" + pointList );
        log.info("reviewList :" + reviewList );

        model.addAttribute("score1List", score1List);
        model.addAttribute("myQnas", myQnas);
        model.addAttribute("myPoints", pointList);
        model.addAttribute("myReviews", reviewList);

        return "/my/home";
    }

    @GetMapping("/my/coupon")
    public String coupon(Model model, @AuthenticationPrincipal Object principal) {
        MemberEntity memberEntity = ((MyUserDetails) principal).getMember();
        String uid = memberEntity.getUid();
        List<MemberCouponDTO> couponDTOList = myService.findCouponByUid(uid);
        model.addAttribute("coupons", couponDTOList);
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

    @GetMapping("/my/couponList")
    @ResponseBody
    public List<MemberCouponDTO> couponList(@AuthenticationPrincipal Object principal) {
        MemberEntity memberEntity = ((MyUserDetails) principal).getMember();
        String uid = memberEntity.getUid();
        return myService.findCouponByUid(uid);
    }

    @GetMapping("/my/point")
    public String point() {
        return "/my/point";
    }

    @GetMapping("/my/qna")
    public String qna(Model model, @AuthenticationPrincipal Object principal, CsPageRequestDTO csPageRequestDTO) {
        String uid = ((MyUserDetails) principal).getMember().getUid();
        CsPageResponseDTO csPageResponseDTO = myService.findByUid(uid,csPageRequestDTO);
        model.addAttribute(csPageResponseDTO);
        return "/my/qna";
    }


    @GetMapping("/my/review")
    public String review() {
        return "/my/review";
    }
}