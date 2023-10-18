package co.kr.lotte.controller.admin;

import co.kr.lotte.dto.product.PageRequestDTO;
import co.kr.lotte.dto.product.PageResponseDTO;

import co.kr.lotte.entity.product.ProductEntity;
import co.kr.lotte.repository.product.ProductRepository;
import co.kr.lotte.service.CateService;
import co.kr.lotte.service.product.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CateService cateService;

    @GetMapping("/admin/index")
    public String index() {
        return ("/admin/index");
    }
    // admin-product
    @GetMapping("/admin/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        // 상품 목록 조회
        PageResponseDTO pageResponseDTO = productService.findByCate1AndCate2(pageRequestDTO);

        // 카테고리 조회
        String c1Name = cateService.getC1Name(pageRequestDTO.getCate1());
        String c2Name = cateService.getC2Name(pageRequestDTO.getCate1(), pageRequestDTO.getCate2());
        log.info("c1Name : " + c1Name);
        log.info("c2Name : " + c2Name);
        model.addAttribute(pageResponseDTO);
        model.addAttribute("c1Name", c1Name);
        model.addAttribute("c2Name", c2Name);
        return "/admin/product/list";
    }
    @GetMapping("/admin/product/register")
    public String pro_register() {
        return ("/admin/product/register");
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