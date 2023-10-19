package co.kr.lotte.service.admin;

import co.kr.lotte.dto.admin.PageRequestDTO;
import co.kr.lotte.dto.admin.PageResponseDTO;
import co.kr.lotte.dto.product.ProductDTO;
import co.kr.lotte.entity.product.ProductCate1Entity;
import co.kr.lotte.entity.product.ProductCate2Entity;
import co.kr.lotte.entity.product.ProductEntity;
import co.kr.lotte.repository.product.Cate1Repository;
import co.kr.lotte.repository.product.Cate2Repository;
import co.kr.lotte.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public PageResponseDTO findByDeleteYn(PageRequestDTO pageRequestDTO){

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

    public List<ProductCate1Entity> cateList(){
        return productCate1Repository.findAll();
    }

    public List<ProductCate2Entity> cate2List(int cate1){

        log.info("service cate2 : " + productCate2Repository.findByCate2(cate1));

        return productCate2Repository.findByCate2(cate1);
    }

    public void save(ProductDTO dto) {
        // 랜덤한 파일 이름 생성
        String randomFilename1 = generateRandomFilename(dto.getThumb1());
        String randomFilename2 = generateRandomFilename(dto.getThumb2());
        String randomFilename3 = generateRandomFilename(dto.getThumb3());
        String randomDetailFilename = generateRandomFilename(dto.getDetail());


        ProductEntity entity = dto.toEntity();

        // ProductDTO에 랜덤한 파일 이름 설정
        entity.setThumb1(randomFilename1);
        entity.setThumb2(randomFilename2);
        entity.setThumb3(randomFilename3);
        entity.setDetail(randomDetailFilename);

        // 나머지 저장 로직
        ProductEntity productEntity = productRepository.save(entity);

        // 파일을 경로에 저장
        String uploadPath = "src/main/resources/static/thumb/"+dto.getProdCate1()+"/"+dto.getProdCate2()+"/";
        saveFile(uploadPath, dto.getThumb1(), randomFilename1);
        saveFile(uploadPath, dto.getThumb2(), randomFilename2);
        saveFile(uploadPath, dto.getThumb3(), randomFilename3);
        saveFile(uploadPath, dto.getDetail(), randomDetailFilename);
    }

    // 파일을 실제 경로에 저장하는 메소드
    private void saveFile(String uploadPath, String fileData, String fileName) {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            byte[] bytes = fileData.getBytes();
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // UUID를 사용하여 랜덤한 파일 이름을 생성하고 원래 파일의 확장자를 유지
    private String generateRandomFilename(String originalFilename) {
        String fileExtension = "";
        int lastDotIndex = originalFilename.lastIndexOf(".");

        if (lastDotIndex >= 0) {
            fileExtension = originalFilename.substring(lastDotIndex);
        }

        UUID uuid = UUID.randomUUID();
        String randomFilename = uuid.toString() + fileExtension;

        return randomFilename;
    }
}