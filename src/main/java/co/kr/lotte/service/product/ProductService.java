package co.kr.lotte.service.product;

import co.kr.lotte.dto.product.PageRequestDTO;
import co.kr.lotte.dto.product.PageResponseDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.entity.product.ProductEntity;
import co.kr.lotte.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

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
}