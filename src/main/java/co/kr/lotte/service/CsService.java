package co.kr.lotte.service;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.BoardTypeDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.repository.cs.BoardCateRepository;
import co.kr.lotte.repository.cs.BoardTypeRepository;
import co.kr.lotte.repository.cs.CsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Page<BoardEntity> result = csRepository.findByCate(csPageRequestDTO.getCate(), pageable);

        List<BoardDTO> dtoList = result.getContent()
                                        .stream()
                                        .map(entity -> modelMapper.map(entity, BoardDTO.class ))
                                        .toList();
        List<BoardTypeEntity> boardTypeEntities = typeRepository.findByCate(csPageRequestDTO.getCate());
        List<BoardCateEntity> boardCateEntities = boardCateRepository.findByCate(csPageRequestDTO.getCate());

        Map<Integer, String > typeMap = new HashMap<>();
        for (BoardTypeEntity boardEntity : boardTypeEntities) {
            typeMap.put(boardEntity.getType(), boardEntity.getTypeName());
        }
        for (BoardDTO boardDTO : dtoList) {
            boardDTO.setTypeName(typeMap.get(boardDTO.getType()));
            log.info("typeName : " + boardDTO.getTypeName());
        }
        Map<String, String > cateMap = new HashMap<>();
        for (BoardCateEntity boardCateEntity : boardCateEntities) {
            cateMap.put(boardCateEntity.getCate(), boardCateEntity.getCateName());
        }
        for (BoardDTO boardDTO : dtoList) {
            boardDTO.setCateName(cateMap.get(boardDTO.getCate()));
            log.info("setCateName : " + boardDTO.getCateName());
        }


        int totalElement = (int) result.getTotalElements();

        return CsPageResponseDTO.builder()
                                .csPageRequestDTO(csPageRequestDTO)
                                .dtoList(dtoList)
                                .total(totalElement)
                                .build();

    }

    public void save (BoardDTO dto){
        BoardEntity entity = dto.toEntity();
        log.info(entity);
        csRepository.save(entity);
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

}
