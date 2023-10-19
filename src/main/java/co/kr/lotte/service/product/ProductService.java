package co.kr.lotte.service.product;

import co.kr.lotte.dto.product.*;
import co.kr.lotte.entity.product.*;
import co.kr.lotte.repository.product.*;
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
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final ModelMapper modelMapper;
    private final Cate1Repository cate1Repository;
    private final Cate2Repository cate2Repository;

    // 상품 등록 - 카테고리 값 조회
    public List<ProductCate1Entity> getCate1() {
        List<ProductCate1Entity> productCate1Entities = cate1Repository.findAll();
        return productCate1Entities;
    }

    public List<ProductCate2Entity> findByCate1(int cate2){
        return cate2Repository.findByCate1(cate2);
    }

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
    // 상품 전체 조회
    public PageResponseDTO findByAll(PageRequestDTO pageRequestDTO) {
        Page<ProductEntity> result = productRepository.findAll(pageRequestDTO.getPageable());

        List<ProductDTO> dtoList = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                .toList();

        int totalElement = (int) result.getTotalElements();

        return PageResponseDTO.builder()
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

    public int saveOrder(ProductOrderDTO productOrderDTO) {
        ProductOrderEntity productOrderEntity = productOrderRepository.save(productOrderDTO.toEntity());
        return productOrderEntity.getOrdNo();
    }

    public void saveOrderItem(List<SearchDTO> searchDTOS, int ordNo) {
        for (SearchDTO searchDTO : searchDTOS) {
            productOrderItemRepository.save(ProductOrderItemEntity.builder()
                            .ordNo(ordNo)
                            .prodNo(searchDTO.getProdNo())
                            .count(searchDTO.getCount())
                            .build());
            if (searchDTO.getCartNo() != 0) {
                productCartRepository.deleteById(searchDTO.getCartNo());
            }
        }
    }

    public ProductOrderDTO findProductOrderById(int ordNo) {
        return productOrderRepository.findById(ordNo).get().toDTO();
    }

    public List<ProductOrderItemDTO> findProductOrderItemsByOrdNo(int ordNo) {
        return productOrderItemRepository.findByOrdNo(ordNo)
                                            .stream()
                                            .map(entity -> modelMapper.map(entity, ProductOrderItemDTO.class))
                                            .toList();
    }
}