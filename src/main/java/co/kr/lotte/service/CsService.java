package co.kr.lotte.service;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.repository.cs.BoardCateRepository;
import co.kr.lotte.repository.cs.BoardTypeRepository;
import co.kr.lotte.repository.cs.CsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsService {

    private final CsRepository csRepository;
    private final BoardTypeRepository typeRepository;
    private final BoardCateRepository boardCateRepository;
    private final ModelMapper modelMapper;


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
    // 글등록
    public void save (BoardDTO dto){
        BoardEntity entity = dto.toEntity();
        log.info(entity);
        csRepository.save(entity);
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
    public void delete(int bno, BoardDTO dto) {
        BoardEntity entity = dto.toEntity();
        entity = csRepository.findById(bno).orElseThrow(() -> new IllegalArgumentException("Board number not found"));
        log.info(entity);
        csRepository.delete(entity);

    }


    public BoardDTO findByBno(int bno){
        BoardEntity boardEntity = csRepository.findById(bno).orElseThrow(() -> new RuntimeException());
        BoardDTO dto = boardEntity.toDTO();

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
