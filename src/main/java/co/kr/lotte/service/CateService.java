package co.kr.lotte.service;

import co.kr.lotte.dto.product.ProductCate1DTO;
import co.kr.lotte.dto.product.ProductCate2DTO;
import co.kr.lotte.entity.product.ProductCate1Entity;
import co.kr.lotte.entity.product.ProductCate2Entity;
import co.kr.lotte.repository.Cate1Repository;
import co.kr.lotte.repository.Cate2Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CateService {
    private final Cate1Repository cate1Repository;
    private final Cate2Repository cate2Repository;

    public List<ProductCate1DTO> getCate() {
        List<ProductCate1Entity> productCate1Entities =  cate1Repository.findAll();
        List<ProductCate2Entity> productCate2Entities = cate2Repository.findAll();
        List<ProductCate1DTO> productCate1DTOS = new ArrayList<>();
        for (ProductCate1Entity productCate1Entity : productCate1Entities) {
            ProductCate1DTO productCate1DTO = productCate1Entity.toDTO();
            List<ProductCate2DTO> productCate2DTOS = new ArrayList<>();
            for (ProductCate2Entity productCate2Entity : productCate2Entities) {
                ProductCate2DTO productCate2DTO = productCate2Entity.toDTO();
                if (productCate2DTO.getCate1() == productCate1DTO.getCate1()) {
                    productCate2DTOS.add(productCate2DTO);
                }
            }
            productCate1DTO.setCate2s(productCate2DTOS);
            productCate1DTOS.add(productCate1DTO);
        }
        return productCate1DTOS;
    }
}