package co.kr.lotte.controller;

import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
        @Autowired
        private ProductService productService;

        @GetMapping(value = {"/", "/index"})
        public String index(Model model, String success) {
                model.addAttribute("success", success);

                // 베스트 상품
                List<ProductDTO> score5List = productService.findTop5ByOrderByScoreDesc();
                // 히트 상품
                List<ProductDTO> hit8List = productService.findTop8ByOrderByHitDesc();
                // 추천 상품
                List<ProductDTO> score8List = productService.findTop8ByOrderByScoreDesc();
                // 최신 상품
                List<ProductDTO> rdate8List = productService.findTop8ByOrderByRdateDesc();
                // 할인 상품
                List<ProductDTO> discount8List = productService.findTop8ByOrderByDiscountDesc();
                model.addAttribute("score5List", score5List);
                model.addAttribute("hit8List", hit8List);
                model.addAttribute("score8List", score8List);
                model.addAttribute("rdate8List", rdate8List);
                model.addAttribute("discount8List", discount8List);
                return "/index";
        }
}