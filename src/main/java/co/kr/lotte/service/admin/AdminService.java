package co.kr.lotte.service.admin;

import co.kr.lotte.dto.admin.PageRequestDTO;
import co.kr.lotte.dto.admin.PageResponseDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.entity.product.ProductCate1Entity;
import co.kr.lotte.entity.product.ProductCate2Entity;
import co.kr.lotte.entity.product.ProductEntity;
import co.kr.lotte.repository.cs.CsRepository;
import co.kr.lotte.repository.product.Cate1Repository;
import co.kr.lotte.repository.product.Cate2Repository;
import co.kr.lotte.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminService {
    private final ProductRepository productRepository;
    private final Cate1Repository productCate1Repository;
    private final Cate2Repository productCate2Repository;
    private final ModelMapper modelMapper;
    private final CsRepository csRepository;

    public PageResponseDTO findByDeleteYn(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("prodNo");
        Page<ProductEntity> result = productRepository.findByDeleteYn("N", pageable);
        List<ProductEntity> dtoList = result.getContent()
                .stream()
                .map(dto -> modelMapper.map(dto, ProductEntity.class))
                .toList();

        int totalElement = (int) result.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }

    public List<ProductCate1Entity> cateList() {
        return productCate1Repository.findAll();
    }

    public List<ProductCate2Entity> cate2List(int cate1) {

        log.info("service cate2 : " + productCate2Repository.findByCate2(cate1));

        return productCate2Repository.findByCate2(cate1);
    }

    public void save(ProductDTO dto) {
        // 파일 저장 경로
        String uploadPath = "src/main/resources/static/thumb/" + dto.getProdCate1() + "/" + dto.getProdCate2() + "/";
        //파일 업로드
        dto.setThumb1(fileSave(dto.getPro_thumb1(), uploadPath));
        dto.setThumb2(fileSave(dto.getPro_thumb2(), uploadPath));
        dto.setThumb3(fileSave(dto.getPro_thumb3(), uploadPath));
        dto.setDetail(fileSave(dto.getPro_detail(), uploadPath));

        ProductEntity entity = dto.toEntity();
        //파일 세이브
        ProductEntity productEntity = productRepository.save(entity);

    }

    public String fileSave(MultipartFile mf, String filePath) {
        String sName = "";
        if (!mf.isEmpty()) {
            String path = new File(filePath).getAbsolutePath();

            String oName = mf.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            sName = UUID.randomUUID().toString() + ext;

            try {
                mf.transferTo(new File(path, sName));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return sName;
    }

    //삭제
    //admin-product
    @Transactional
    public int deleteByProdNo(List<Integer> prodNos) {
        int deletedCount = 0;

        for (Integer prodNo : prodNos) {
            // 데이터베이스에서 상품 삭제
            int result = productRepository.deleteByProdNo(prodNo);
            deletedCount += result;
        }

        return deletedCount;
    }
    //admin-cs
    @Transactional
    public int deleteByBno(List<Integer> bnos) {
        int deletedCount = 0;

        for (Integer bno : bnos) {
            int result = csRepository.deleteByBno(bno); // 단일 bno에 대한 메서드 사용
            deletedCount += result;
        }

        return deletedCount;
    }

}