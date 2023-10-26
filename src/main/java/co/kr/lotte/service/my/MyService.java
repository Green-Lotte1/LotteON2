package co.kr.lotte.service.my;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.BoardFileDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.dto.member.MemberCouponDTO;
import co.kr.lotte.dto.member.MemberPointDTO;
import co.kr.lotte.dto.my.MemberPointPageResponseDTO;
import co.kr.lotte.dto.my.PageResponseDTO;
import co.kr.lotte.dto.my.ReviewPageResponseDTO;
import co.kr.lotte.dto.my.SearchDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.dto.product.ProductOrderDTO;
import co.kr.lotte.dto.product.ProductOrderItemDTO;
import co.kr.lotte.dto.product.ProductReviewDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardFileEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.entity.member.MemberCouponEntity;
import co.kr.lotte.entity.member.MemberPointEntity;
import co.kr.lotte.entity.product.ProductEntity;
import co.kr.lotte.entity.product.ProductOrderEntity;
import co.kr.lotte.entity.product.ProductOrderItemEntity;
import co.kr.lotte.entity.product.ProductReviewEntity;
import co.kr.lotte.repository.cs.BoardCateRepository;
import co.kr.lotte.repository.cs.BoardFileRepository;
import co.kr.lotte.repository.cs.BoardTypeRepository;
import co.kr.lotte.repository.cs.CsRepository;
import co.kr.lotte.repository.member.MemberCouponRepository;
import co.kr.lotte.repository.member.MemberPointRepository;
import co.kr.lotte.repository.product.ProductOrderItemRepository;
import co.kr.lotte.repository.product.ProductOrderRepository;
import co.kr.lotte.repository.product.ProductRepository;
import co.kr.lotte.repository.product.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class MyService {
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CsRepository csRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final BoardCateRepository boardCateRepository;
    private final MemberPointRepository memberPointRepository;
    private final ProductReviewRepository productReviewRepository;
    private final MemberCouponRepository memberCouponRepository;

    // 전체 주문 내역
    public PageResponseDTO findOrderList(String uid, SearchDTO searchDTO) {

        // DateTimeFormatter를 사용하여 문자열을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // 시작일이니 00시 00분 00초로 format
        LocalDateTime begin = LocalDateTime.parse(searchDTO.getBegin() + "T00:00:00", formatter);
        // 종료일이니 23시 59분 59초로 format
        LocalDateTime end = LocalDateTime.parse(searchDTO.getEnd() + "T23:59:59", formatter);
        Page<ProductOrderItemEntity> result = productOrderItemRepository.findByOrdUidAndOrdDateBetween(uid, begin, end, searchDTO.getPageable());
        List<ProductOrderItemDTO> dtoList = result
                .getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductOrderItemDTO.class))
                .toList();
        for (ProductOrderItemDTO productOrderItemDTO : dtoList) {
            ProductDTO productDTO = productRepository.findById(productOrderItemDTO.getProdNo()).get().toDTO();
            productOrderItemDTO.setProduct(productDTO);
            productOrderItemDTO.StatusStringSet();
        }
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .searchDTO(searchDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }

    // 포인트 내역
    public MemberPointPageResponseDTO findPointList(String uid, SearchDTO searchDTO) {

        // DateTimeFormatter를 사용하여 문자열을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // 시작일이니 00시 00분 00초로 format
        LocalDateTime begin = LocalDateTime.parse(searchDTO.getBegin() + "T00:00:00", formatter);
        // 종료일이니 23시 59분 59초로 format
        LocalDateTime end = LocalDateTime.parse(searchDTO.getEnd() + "T23:59:59", formatter);
        Page<MemberPointEntity> result = memberPointRepository.findByUidAndPointDateBetween(uid, begin, end, searchDTO.getPageable());
        List<MemberPointDTO> dtoList = result
                .getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, MemberPointDTO.class))
                .toList();
        int totalElement = (int) result.getTotalElements();
        return MemberPointPageResponseDTO.builder()
                .searchDTO(searchDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }

    // 리뷰 내역
    public ReviewPageResponseDTO findReviewList(String uid, SearchDTO searchDTO) {
        Page<ProductReviewEntity> result = productReviewRepository.findByUid(uid,  searchDTO.getPageable());
        List<ProductReviewDTO> dtoList = result
                .getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductReviewDTO.class))
                .toList();
        for (ProductReviewDTO productReviewDTO : dtoList) {
            ProductDTO product = productRepository.findById(productReviewDTO.getProdNo()).get().toDTO();
            productReviewDTO.setProduct(product);
        }
        int totalElement = (int) result.getTotalElements();
        return ReviewPageResponseDTO.builder()
                .searchDTO(searchDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }

    //QNA LIST
   public CsPageResponseDTO findByUid(String uid, CsPageRequestDTO csPageRequestDTO){

        Pageable pageable = csPageRequestDTO.getPageable("bno");

        List<BoardCateEntity> boardCateEntitieList =  boardCateRepository.findAll();
        List<BoardTypeEntity> boardTypeEntitieList = boardTypeRepository.findAll();

        Map<String, Map<Integer, String>> cateMap = new HashMap<>();
        for (BoardCateEntity boardCateEntity : boardCateEntitieList) {
            Map<Integer, String > typeMap = new HashMap<>();
            for (BoardTypeEntity boardEntity : boardTypeEntitieList) {
                if (boardEntity.getCate().equals(boardCateEntity.getCate())) {
                    typeMap.put(boardEntity.getType(), boardEntity.getTypeName());
                }
            }
            cateMap.put(boardCateEntity.getCate(), typeMap);
        }


        Page<BoardEntity> result = csRepository.findByUid(uid, pageable);

        List<BoardDTO> dtoList = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, BoardDTO.class ))
                .toList();

        for (BoardDTO boardDTO : dtoList) {
            boardDTO.setTypeName(
                    cateMap.get(boardDTO.getCate()).get(boardDTO.getType())

            );
        }

        int totalElement = (int) result.getTotalElements();

        return CsPageResponseDTO.builder()
                .csPageRequestDTO(csPageRequestDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();

    }
    // Home_OrderList

    public List<ProductOrderItemDTO> findTop1ByOrdUidOrderByOrdDateDesc(String ordUid) {

        List<ProductOrderItemEntity> result = productOrderItemRepository.findTop1ByOrdUidOrderByOrdDateDesc(ordUid);

        List<ProductOrderItemDTO> dtoList = result
                .stream()
                .map(entity -> modelMapper.map(entity, ProductOrderItemDTO.class))
                .toList();
        for (ProductOrderItemDTO productOrderItemDTO : dtoList) {
            ProductDTO productDTO = productRepository.findById(productOrderItemDTO.getProdNo()).get().toDTO();
            productOrderItemDTO.setProduct(productDTO);
            productOrderItemDTO.StatusStringSet();
        }

        return dtoList;

    }
    // Home_QnA
    public List<BoardDTO> findTop5ByUidOrderByRdateDesc(String uid) {

        List<BoardEntity> boardEntityPage = csRepository.findTop5ByUidOrderByRdateDesc(uid);
        List<BoardDTO> dtoList = boardEntityPage
                .stream()
                .map(entity -> modelMapper.map(entity, BoardDTO.class ))
                .toList();

        List<BoardCateEntity> boardCateEntitieList =  boardCateRepository.findAll();
        List<BoardTypeEntity> boardTypeEntitieList = boardTypeRepository.findAll();

        Map<String, Map<Integer, String>> cateMap = new HashMap<>();
        for (BoardCateEntity boardCateEntity : boardCateEntitieList) {
            Map<Integer, String > typeMap = new HashMap<>();
            for (BoardTypeEntity boardEntity : boardTypeEntitieList) {
                if (boardEntity.getCate().equals(boardCateEntity.getCate())) {
                    typeMap.put(boardEntity.getType(), boardEntity.getTypeName());
                }
            }
            cateMap.put(boardCateEntity.getCate(), typeMap);
        }

        for (BoardDTO boardDTO : dtoList) {
            boardDTO.setTypeName(
                    cateMap.get(boardDTO.getCate()).get(boardDTO.getType())
            );
        }
        return dtoList;

    }


    // Home_Point
    public List<MemberPointDTO> findPointByUid(String uid) {
        List<MemberPointEntity> result = memberPointRepository.findByUid(uid);
        List<MemberPointDTO> dtoList = result
                .stream()
                .map(entity -> modelMapper.map(entity, MemberPointDTO.class))
                .toList();
        return dtoList;
    }

    // Home_Review
    public List<ProductReviewDTO> findReviewByUid(String uid) {
        List<ProductReviewEntity> result = productReviewRepository.findByUid(uid);
        List<ProductReviewDTO> dtoList = result
                .stream()
                .map(entity -> modelMapper.map(entity, ProductReviewDTO.class))
                .toList();
        for (ProductReviewDTO productReviewDTO : dtoList) {
            ProductDTO productDTO = productRepository.findById(productReviewDTO.getProdNo()).get().toDTO();
            productReviewDTO.setProduct(productDTO);
        }

        return dtoList;

    }

    // 쿠폰 가져오기
    public List<MemberCouponDTO> findCouponByUid(String uid) {
        List<MemberCouponEntity> memberCouponEntityList = memberCouponRepository.findByUidAndUseYn(uid, "Y");
        List<MemberCouponDTO> memberCouponDTOList = memberCouponEntityList.stream()
                                                                            .map(entity -> modelMapper.map(entity, MemberCouponDTO.class ))
                                                                            .toList();
        for (MemberCouponDTO memberCouponDTO : memberCouponDTOList) {
            memberCouponDTO.changeUseYnString();
        }
        return  memberCouponDTOList;
    }
}