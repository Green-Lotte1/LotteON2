package co.kr.lotte.controller.product;

import co.kr.lotte.dto.product.PageRequestDTO;
import co.kr.lotte.dto.product.PageResponseDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.dto.product.SearchDTO;
import co.kr.lotte.service.CateService;
import co.kr.lotte.service.product.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CateService cateService;

    @GetMapping("/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        // 상품 목록 조회
        PageResponseDTO pageResponseDTO = productService.findByCate1AndCate2(pageRequestDTO);
        log.info("pageResponseDTO pg : " + pageResponseDTO.getPg());
        log.info("pageResponseDTO size : " + pageResponseDTO.getSize());
        log.info("pageResponseDTO total : " + pageResponseDTO.getTotal());
        log.info("pageResponseDTO start : " + pageResponseDTO.getStart());
        log.info("pageResponseDTO end : " + pageResponseDTO.getEnd());
        log.info("pageResponseDTO prev : " + pageResponseDTO.isPrev());
        log.info("pageResponseDTO next : " + pageResponseDTO.isNext());
        log.info("pageResponseDTO dtoList : " + pageResponseDTO.getDtoList().size());

        // 카테고리 조회
        String c1Name = cateService.getC1Name(pageRequestDTO.getCate1());
        String c2Name = cateService.getC2Name(pageRequestDTO.getCate1(), pageRequestDTO.getCate2());
        log.info("c1Name : " + c1Name);
        log.info("c2Name : " + c2Name);
        model.addAttribute(pageResponseDTO);
        model.addAttribute("c1Name", c1Name);
        model.addAttribute("c2Name", c2Name);
        return "/product/list";
    }

    @GetMapping("/product/view")
    public String view(Model model, SearchDTO searchDTO) {
        ProductDTO productDTO = productService.getProductDTO(searchDTO.getProdNo());
        model.addAttribute("product", productDTO);
        model.addAttribute("cate1", searchDTO.getCate1());
        model.addAttribute("cate2", searchDTO.getCate2());
        return "/product/view";
    }

    @GetMapping("/product/cart")
    public String cart() {
        return "/product/cart";
    }

    @GetMapping("/product/complete")
    public String complete() {
        return "/product/complete";
    }

    @GetMapping("/product/order")
    public String order() {
        return "/product/order";
    }

    @GetMapping("/product/search")
    public String search() {
        return "/product/search";
    }
}