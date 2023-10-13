package co.kr.lotte.controller;

import co.kr.lotte.dto.product.ProductCate1DTO;
import co.kr.lotte.dto.product.ProductCate2DTO;
import co.kr.lotte.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CateController {
    @Autowired
    private CateService cateService;

    @GetMapping("/cate")
    public String cate() {
        List<ProductCate1DTO> productCate1DTOS = cateService.getCate();
        StringBuilder cate = new StringBuilder("");
        cate.append("<li><i class=\"fa fa-bars\" aria-hidden=\"true\"></i>카테고리</li>");
        for (ProductCate1DTO cate1 : productCate1DTOS) {
            cate.append("<li>");
            cate.append("<a href=\"#\"><i class=\"fas " + cate1.getCss() + "\"></i>" + cate1.getC1Name() + "</a>");
            cate.append("<ol>");
            for (ProductCate2DTO cate2 : cate1.getCate2s()) {
                if (cate2.getCate1() == cate1.getCate1()) {
                    cate.append("<li><a href=\"/product/list?pg=1&cate1="+cate1.getCate1()+"&cate2=" + cate2.getCate2() + "\">" + cate2.getC2Name() + "</a></li>");
                }
            }
            cate.append("</ol>");
            cate.append("</li>");
        }
        return cate.toString();
    }
}