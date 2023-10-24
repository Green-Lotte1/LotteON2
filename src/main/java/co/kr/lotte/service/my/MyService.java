package co.kr.lotte.service.my;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.BoardFileDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.dto.my.PageResponseDTO;
import co.kr.lotte.dto.my.SearchDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.dto.product.ProductOrderDTO;
import co.kr.lotte.dto.product.ProductOrderItemDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardFileEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.entity.product.ProductOrderEntity;
import co.kr.lotte.entity.product.ProductOrderItemEntity;
import co.kr.lotte.repository.cs.BoardCateRepository;
import co.kr.lotte.repository.cs.BoardFileRepository;
import co.kr.lotte.repository.cs.BoardTypeRepository;
import co.kr.lotte.repository.cs.CsRepository;
import co.kr.lotte.repository.product.ProductOrderItemRepository;
import co.kr.lotte.repository.product.ProductOrderRepository;
import co.kr.lotte.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        }
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .searchDTO(searchDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }


    //QNA LIST
   public CsPageResponseDTO findByCate(String uid, CsPageRequestDTO csPageRequestDTO){

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
}