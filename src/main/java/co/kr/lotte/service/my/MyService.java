package co.kr.lotte.service.my;

import co.kr.lotte.dto.my.PageResponseDTO;
import co.kr.lotte.dto.my.SearchDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.dto.product.ProductOrderDTO;
import co.kr.lotte.dto.product.ProductOrderItemDTO;
import co.kr.lotte.entity.product.ProductOrderEntity;
import co.kr.lotte.entity.product.ProductOrderItemEntity;
import co.kr.lotte.repository.product.ProductOrderItemRepository;
import co.kr.lotte.repository.product.ProductOrderRepository;
import co.kr.lotte.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyService {
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public PageResponseDTO findOrderList(String uid, SearchDTO searchDTO) {

        // DateTimeFormatter를 사용하여 문자열을 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // 시작일이니 00시 00분 00초로 format
        LocalDateTime begin = LocalDateTime.parse(searchDTO.getBegin() + "T00:00:00", formatter);
        // 종료일이니 23시 59분 59초로 format
        LocalDateTime end = LocalDateTime.parse(searchDTO.getEnd() + "T23:59:59", formatter);
        Page<ProductOrderItemEntity> result = productOrderItemRepository.findByOrdUidAndOrdDateBetween(uid, begin, end, searchDTO.getPageable());
        List<ProductOrderItemDTO>dtoList =  result
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
}