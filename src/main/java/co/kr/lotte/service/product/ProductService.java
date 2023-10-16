package co.kr.lotte.service.product;

import co.kr.lotte.dto.product.PageRequestDTO;
import co.kr.lotte.dto.product.PageResponseDTO;
import co.kr.lotte.dto.product.ProductCartDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.entity.product.ProductCartEntity;
import co.kr.lotte.entity.product.ProductEntity;
import co.kr.lotte.repository.product.ProductCartRepository;
import co.kr.lotte.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCartRepository productCartRepository;
    private final ModelMapper modelMapper;

    // 상품 목록 조회
    public PageResponseDTO findByCate1AndCate2(PageRequestDTO pageRequestDTO) {
        Page<ProductEntity> result = productRepository.findByProdCate1AndProdCate2(pageRequestDTO.getCate1(), pageRequestDTO.getCate2(), pageRequestDTO.getPageable());
        List<ProductDTO> dtoList = result
                                    .getContent()
                                    .stream()
                                    .map(entity -> modelMapper.map(entity, ProductDTO.class))
                                    .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }

    // 상품 상세 조회
    public ProductDTO getProductDTO(int prodNo) {
        ProductEntity productEntity = productRepository.findById(prodNo).get();
        return productEntity.toDTO();
    }

    public int insertProductCart(ProductCartDTO productCartDTO) {
        // 이미 장바구니 등록된 물품인지 확인
        ProductCartEntity productCartEntity = productCartRepository.findByUidAndProdNo(productCartDTO.getUid(), productCartDTO.getProdNo());
        ProductCartEntity saved;
        // 이미 장바구니에 등록된 물품일경우 count만 올림
        if (productCartEntity != null) {
            productCartEntity.setCount(productCartEntity.getCount() + productCartDTO.getCount());
            saved = productCartRepository.save(productCartEntity);
        } else {
            saved = productCartRepository.save(productCartDTO.toEntity());
        }
        return saved.getCartNo();
    }

    public List<ProductCartDTO> findProductCartByUid(String uid) {
        List<ProductCartEntity> productCartEntities = productCartRepository.findByUid(uid);
        List<ProductCartDTO> productCartDTOS = new ArrayList<>();
        for (ProductCartEntity productCart : productCartEntities) {
            ProductCartDTO productCartDTO = productCart.toDTO();
            Optional<ProductEntity> productEntity = productRepository.findById(productCartDTO.getProdNo());
            productCartDTO.setProduct(productEntity.get().toDTO());
            productCartDTOS.add(productCartDTO);
        }
        return productCartDTOS;
    }

    public void deleteCart(int[] chks) {
        for(int chk : chks) {
            log.info("chk : " + chk);
            productCartRepository.deleteById(chk);
        }
    }

    public ProductDTO findProductById(int prodNo) {
        return productRepository.findById(prodNo).get().toDTO();
    }

    public List<ProductDTO> findTop5ByOrderByScoreDesc() {
        return productRepository.findTop5ByOrderByScoreDesc()
                                .stream()
                                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                                .toList();
    }

    public List<ProductDTO> findTop8ByOrderByScoreDesc() {
        return productRepository.findTop8ByOrderByScoreDesc()
                                .stream()
                                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                                .toList();
    }

    public List<ProductDTO> findTop8ByOrderByHitDesc() {
        return productRepository.findTop8ByOrderByHitDesc()
                                .stream()
                                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                                .toList();
    }

    public List<ProductDTO> findTop8ByOrderByRdateDesc() {
        return productRepository.findTop8ByOrderByRdateDesc()
                                .stream()
                                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                                .toList();
    }

    public List<ProductDTO> findTop8ByOrderByDiscountDesc() {
        return productRepository.findTop8ByOrderByDiscountDesc()
                                .stream()
                                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                                .toList();
    }
}