package co.kr.lotte.service;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.BoardFileDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardFileEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.repository.cs.BoardCateRepository;
import co.kr.lotte.repository.cs.BoardFileRepository;
import co.kr.lotte.repository.cs.BoardTypeRepository;
import co.kr.lotte.repository.cs.CsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsService {

    private final CsRepository csRepository;
    private final BoardTypeRepository typeRepository;
    private final BoardCateRepository boardCateRepository;
    private final ModelMapper modelMapper;
    private final BoardFileRepository fileRepository;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    private final ResourceLoader resourceLoader;




    public CsPageResponseDTO findByCate(CsPageRequestDTO csPageRequestDTO){

        Pageable pageable = csPageRequestDTO.getPageable("bno");

        List<BoardCateEntity> boardCateEntitieList =  boardCateRepository.findAll();
        List<BoardTypeEntity> boardTypeEntitieList = typeRepository.findAll();

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

        Page<BoardEntity> result = csRepository.findByCate(csPageRequestDTO.getCate(), pageable);

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
    // 글등록 및 파일등록
    public void save (BoardDTO dto){
        BoardEntity savedEntity = csRepository.save(dto.toEntity());
        log.info(savedEntity);

        BoardFileDTO fileDTO = fileUpload(dto);

        if(fileDTO!= null) {
            fileDTO.setBno(savedEntity.getBno());
            fileRepository.save(fileDTO.toEntity());
            savedEntity.setFile(1);
            csRepository.save(savedEntity);
            log.info("fileEntity...1");

        }


    }
    // 파일 등록
    @Value("src/main/resources/static/thumb/cs/qna/")
    private String filePath;

    public BoardFileDTO fileUpload(BoardDTO dto){
        MultipartFile mf = dto.getFname();

        if(!mf.isEmpty()) {
            String path = new File(filePath).getAbsolutePath();

            String oName = mf.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String sName = UUID.randomUUID().toString() + ext;

            try{
                mf.transferTo(new File(path, sName));
            }catch (IOException e){
                log.error(e.getMessage());
            }

            return BoardFileDTO.builder().ofile(oName).sfile(sName).build();
        }

        return null;
    }

    public BoardFileEntity getSfileByFno(int fno){
        BoardFileEntity sfileByFno = fileRepository.findSfileByFno(fno);
        return sfileByFno;
    }

    // 파일 절대경로
    public String getAbsoluteFilePath(String sfile){

        try {
            Resource resource = resourceLoader.getResource("classpath:static/thumb/cs/qna/" + sfile);
            return resource.getFile().getAbsolutePath();
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }


    // 파일 다운로드
    public ResponseEntity<Resource> fileDownload(String sfileName,String ofileName) throws IOException{
        //String absoluteFilePath = "C:\\Users\\Java\\Desktop\\Workspace\\LotteON2\\build\\resources\\main\\static\\thumb\\cs\\qna\\"+"8c303751-4f64-4d77-ab80-575158f4b294.png";


        Path filePath = Paths.get(getAbsoluteFilePath(sfileName));

            if (Files.exists(filePath)) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toString()));
            String fileName =  filePath.getFileName().toString();
            log.info("Success download input excel file : " + filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .cacheControl(CacheControl.noCache())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ofileName)
                    .body(resource);
            } else {
                // 파일이 존재하지 않는 경우에 대한 예외 처리
                log.error("File not found: " + filePath);
                return ResponseEntity.notFound().build();
            }
    }

    // 글수정
    @Transactional
    public void update(int bno, BoardDTO dto) {
        BoardEntity entity = dto.toEntity();
        entity = csRepository.findById(bno).orElseThrow(() -> new IllegalArgumentException("Board number not found"));
        log.info(entity);
        entity.setContent(dto.getContent());
        csRepository.save(entity);

    }

    // 글삭제
    public void delete(int bno) {
        BoardEntity entity = csRepository.findById(bno).orElseThrow(() -> new IllegalArgumentException("Board number not found"));
        log.info(entity);
        csRepository.delete(entity);

    }


    public BoardDTO findByBnoForBoard(int bno){
        BoardEntity boardEntity = csRepository.findById(bno).orElseThrow(() -> new RuntimeException());
        List<BoardFileEntity> boardFileEntities = fileRepository.findByBno(bno);

        List<BoardFileDTO> boardFileDTOS = boardFileEntities
                .stream()
                .map(entity -> modelMapper.map(entity, BoardFileDTO.class ))
                .toList();


        BoardDTO dto = boardEntity.toDTO();
        dto.setFileDTOList(boardFileDTOS);

        List<BoardTypeEntity> boardTypeEntities = typeRepository.findByCate(dto.getCate());
        log.info("getCate : "+ dto.getCate());
        log.info("getType : "+ dto.getType());

        Map<Integer, String > typeMap = new HashMap<>();
        for (BoardTypeEntity boardTypeEntity : boardTypeEntities) {
            typeMap.put(boardTypeEntity.getType(), boardTypeEntity.getTypeName());
        }
        dto.setTypeName(typeMap.get(dto.getType()));
        log.info("getTypeName : "+ dto.getTypeName());

        return dto;

    }

    public List<BoardDTO> findByCateForFaq(String cate) {
        List<BoardDTO> dtoList = new ArrayList<>();
        List<BoardTypeEntity> boardTypeEntities = typeRepository.findByCate(cate);
        for(BoardTypeEntity boardTypeEntity : boardTypeEntities){
            List<BoardEntity> boardEntities = csRepository.findTop10ByType(boardTypeEntity.getType());
            List<BoardDTO> boardDTOS = boardEntities
                                        .stream()
                                        .map(entity -> modelMapper.map(entity, BoardDTO.class ))
                                        .toList();
            for(BoardDTO boardDTO : boardDTOS){
                dtoList.add(boardDTO);
            }
        }

        return dtoList;
    }

/*    public List<BoardEntity> getNoticeBoard(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rdate").descending().and(Sort.by("bno").descending()));
        return csRepository.findByGroupAndTypeGreaterThanOrderByRdateDescBnoDesc("notice", 20, pageable);
    }*/

    public List<BoardDTO> getNoticeBoard(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rdate").descending().and(Sort.by("bno").descending()));
        List<BoardEntity> boardEntityPage = csRepository.findByGroupAndTypeGreaterThanOrderByRdateDescBnoDesc("notice", 20, pageable);
        List<BoardDTO> dtoList = boardEntityPage
                                .stream()
                                .map(entity -> modelMapper.map(entity, BoardDTO.class ))
                                .toList();


        List<BoardCateEntity> boardCateEntitieList =  boardCateRepository.findAll();
        List<BoardTypeEntity> boardTypeEntitieList = typeRepository.findAll();

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


    public List<BoardDTO> getQnaBoard(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rdate").descending().and(Sort.by("bno").descending()));

        List<BoardEntity> boardEntityPage = csRepository.findByGroupAndTypeLessThanOrderByRdateDescBnoDesc("qna", 20, pageable);
        List<BoardDTO> dtoList = boardEntityPage
                .stream()
                .map(entity -> modelMapper.map(entity, BoardDTO.class ))
                .toList();


        List<BoardCateEntity> boardCateEntitieList =  boardCateRepository.findAll();
        List<BoardTypeEntity> boardTypeEntitieList = typeRepository.findAll();

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

}
