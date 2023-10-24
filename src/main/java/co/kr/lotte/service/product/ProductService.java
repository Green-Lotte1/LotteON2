package co.kr.lotte.service.product;

import co.kr.lotte.dto.product.*;
import co.kr.lotte.entity.member.MemberEntity;
import co.kr.lotte.entity.member.MemberPointEntity;
import co.kr.lotte.entity.product.*;
import co.kr.lotte.repository.member.MemberPointRepository;
import co.kr.lotte.repository.member.MemberRepository;
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
    private final ProductReviewRepository productReviewRepository;
    private final MemberRepository memberRepository;
    private final MemberPointRepository memberPointRepository;
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

    public void saveOrderItem(List<SearchDTO> searchDTOS, int ordNo, String uid) {
        // 적립될 포인트
        int point = 0;
        for (SearchDTO searchDTO : searchDTOS) {
            // 주문 상품 저장
            ProductOrderItemEntity productOrderItemEntity = ProductOrderItemEntity.builder()
                                                            .ordUid(uid)
                                                            .ordNo(ordNo)
                                                            .prodNo(searchDTO.getProdNo())
                                                            .count(searchDTO.getCount())
                                                            .ordStatus("C")
                                                            .build();
            productOrderItemRepository.save(productOrderItemEntity);
            ProductEntity product = productRepository.findById(productOrderItemEntity.getProdNo()).get();
            int savePoint = product.getPoint() * productOrderItemEntity.getCount();
            point += savePoint;

            // 장바구니에서 주문한 상품일 경우 장바구니 삭제
            if (searchDTO.getCartNo() != 0) {
                productCartRepository.deleteById(searchDTO.getCartNo());
            }
        }
        // 세션의 uid를 이용해서 사용자 객체를 가져옴
        MemberEntity member = memberRepository.findById(uid).get();

        // 주문번호로 주문이력을 가져옴
        ProductOrderEntity productOrder = productOrderRepository.findById(ordNo).get();
        // 적립된 포인트 저장
        productOrder.setSavePoint(point);
        productOrderRepository.save(productOrder);
    }

    public ProductOrderDTO findProductOrderById(int ordNo) {
        ProductOrderDTO productOrderDTO = productOrderRepository.findById(ordNo).get().toDTO();
        MemberEntity memberEntity =  memberRepository.findById(productOrderDTO.getOrdUid()).get();
        productOrderDTO.setOrdUser(memberEntity.getName());
        return productOrderDTO;
    }

    public List<ProductOrderItemDTO> findProductOrderItemsByOrdNo(int ordNo) {
        return productOrderItemRepository.findByOrdNo(ordNo)
                                            .stream()
                                            .map(entity -> modelMapper.map(entity, ProductOrderItemDTO.class))
                                            .toList();
    }

    public void usePoint(String uid, int usedPoint, int ordNo) {
        // uid로 해당 사용자 객체를 가져온다.
        MemberEntity member = memberRepository.findById(uid).get();
        // 현재 포인트 - 사용한 포인트로 바꿔준다.
        member.setPoint(member.getPoint() - usedPoint);
        // 바뀐 포인트가 적용되도록 저장
        memberRepository.save(member);

        MemberPointEntity memberPointEntity = MemberPointEntity.builder()
                                                                .uid(uid)
                                                                .ordNo(ordNo)
                                                                .point(-usedPoint)
                                                                .build();
        memberPointRepository.save(memberPointEntity);
    }

    public boolean checkReview(int prodNo, String uid) {
        ProductReviewEntity productReviewEntity = productReviewRepository.findByProdNoAndUid(prodNo, uid);
        return productReviewEntity != null;
    }

    public boolean checkReceive(int no, String uid) {
        ProductOrderItemEntity productReviewEntity = productOrderItemRepository.findById(no).get();
        return productReviewEntity.getOrdStatus().equals("Z");
    }

    public String orderReceive(int no, String uid) {
        // 주문 품목을 가져옴
        ProductOrderItemEntity productOrderItemEntity = productOrderItemRepository.findById(no).get();
        // 구매확정
        productOrderItemEntity.setOrdStatus("Z");
        // 저장
        ProductOrderItemEntity productOrderItem = productOrderItemRepository.save(productOrderItemEntity);
        String flag = productOrderItem.getOrdStatus().equals("Z") ? "success" : "fail";
        // 정상적으로 작동할 경우 포인트 적립
        if (flag.equals("success")) {
            int prodNo = productOrderItem.getProdNo();
            ProductEntity product = productRepository.findById(prodNo).get();
            MemberEntity member = memberRepository.findById(uid).get();
            // 적립 포인트 계산
            int point = (product.getPoint() * productOrderItem.getCount());
            
            // 포인트 추가
            member.setPoint(member.getPoint() + point);
            // 포인트 저장
            memberRepository.save(member);
            
            // 포인트 이력 추가
            MemberPointEntity memberPointEntity = MemberPointEntity.builder().uid(uid).ordNo(productOrderItem.getOrdNo()).point(point).build();
            memberPointRepository.save(memberPointEntity);
        }
        return flag;
    }
}