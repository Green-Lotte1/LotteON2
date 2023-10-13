package co.kr.lotte.controller.product;

import co.kr.lotte.dto.product.PageRequestDTO;
import co.kr.lotte.dto.product.PageResponseDTO;
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

    @GetMapping("/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        PageResponseDTO pageResponseDTO = productService.findByCate1AndCate2(pageRequestDTO);
        log.info("pageResponseDTO pg : " + pageResponseDTO.getPg());
        log.info("pageResponseDTO size : " + pageResponseDTO.getSize());
        log.info("pageResponseDTO total : " + pageResponseDTO.getTotal());
        log.info("pageResponseDTO start : " + pageResponseDTO.getStart());
        log.info("pageResponseDTO end : " + pageResponseDTO.getEnd());
        log.info("pageResponseDTO prev : " + pageResponseDTO.isPrev());
        log.info("pageResponseDTO next : " + pageResponseDTO.isNext());
        log.info("pageResponseDTO dtoList : " + pageResponseDTO.getDtoList().size());
        model.addAttribute(pageResponseDTO);
        return "/product/list";
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
    @GetMapping("/product/view")
    public String view() {
        return "/product/view";
    }
}