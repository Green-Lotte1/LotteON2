package co.kr.lotte.controller.admin;

import co.kr.lotte.dto.product.PageRequestDTO;
import co.kr.lotte.dto.product.PageResponseDTO;

import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.entity.product.ProductCate1Entity;
import co.kr.lotte.entity.product.ProductCate2Entity;
import co.kr.lotte.repository.product.ProductRepository;
import co.kr.lotte.security.MyUserDetails;
import co.kr.lotte.service.CateService;
import co.kr.lotte.service.admin.AdminService;
import co.kr.lotte.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CateService cateService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/index")
    public String index() {
        return ("/admin/index");
    }
    // admin-product
    @GetMapping("/admin/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        // 서비스를 사용하여 전체 제품 데이터를 검색
        PageResponseDTO pageResponseDTO = productService.findByAll(pageRequestDTO);

        // 모델에 데이터 추가
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        // Thymeleaf 템플릿의 이름 반환
        return "/admin/product/list";
    }


    @GetMapping("/admin/product/register")
    public String pro_register(HttpServletRequest request, Model model) {

        List<ProductCate1Entity> cates1 = productService.getCate1();
        model.addAttribute("cates1",cates1);
        log.info("cates1 : "+ cates1);

        return ("/admin/product/register");
    }

    @PostMapping("/admin/product/register")
    public String pro_register3(HttpServletRequest request, ProductDTO dto) {
        dto.setIp(request.getRemoteAddr());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MyUserDetails) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            String sellerId = userDetails.getMember().getName(); // 현재 로그인한 사용자의 판매자 ID
            dto.setSeller(sellerId); // ProductDTO에 판매자 ID 설정
        }

        log.info("dto: " + dto);
        adminService.save(dto);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/registerCate2")
    @ResponseBody
    public List<ProductCate2Entity> pro_register2(int cate1){

        log.info("optionValue : " + cate1);
        List<ProductCate2Entity> productCate2Entities = productService.findByCate1(cate1);
        log.info("size : " + productCate2Entities.size());
        return productCate2Entities;
    }

    // admin-cs-notice
    @GetMapping("admin/cs/notice/list")
    public String cs_no_list() {
        return ("/admin/cs/notice/list");
    }
    @GetMapping("admin/cs/notice/view")
    public String cs_no_view() {
        return ("/admin/cs/notice/view");
    }
    @GetMapping("admin/cs/notice/write")
    public String cs_no_write() {
        return ("/admin/cs/notice/write");
    }
    @GetMapping("admin/cs/notice/modify")
    public String cs_no_modify() {
        return ("/admin/cs/notice/modify");
    }

    // admin-cs-faq
    @GetMapping("admin/cs/faq/list")
    public String cs_faq_list(){
        return ("/admin/cs/faq/list");
    }
    @GetMapping("admin/cs/faq/view")
    public String cs_faq_view(){
        return ("/admin/cs/faq/view");
    }
    @GetMapping("admin/cs/faq/write")
    public String cs_faq_write(){
        return ("/admin/cs/faq/write");
    }
    @GetMapping("admin/cs/faq/modify")
    public String cs_faq_modify(){
        return ("/admin/cs/faq/modify");
    }

    // admin-cs-qna
    @GetMapping("admin/cs/qna/list")
    public String cs_qna_list(){
        return ("/admin/cs/qna/list");
    }
    @GetMapping("admin/cs/qna/reply")
    public String cs_qna_reply(){
        return ("/admin/cs/qna/reply");
    }
    @GetMapping("admin/cs/qna/view")
    public String cs_qna_view(){
        return ("/admin/cs/qna/view");
    }

}