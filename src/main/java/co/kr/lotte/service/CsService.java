package co.kr.lotte.service;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import co.kr.lotte.dto.cs.CsPageResponseDTO;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.repository.cs.BoardTypeRepository;
import co.kr.lotte.repository.cs.CsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsService {

    private final CsRepository csRepository;
    private final ModelMapper modelMapper;


    public CsPageResponseDTO findByCate(CsPageRequestDTO csPageRequestDTO){

        Pageable pageable = csPageRequestDTO.getPageable("bno");
        Page<BoardEntity> result = csRepository.findByCate(csPageRequestDTO.getCate(), pageable);

        List<BoardDTO> dtoList = result.getContent()
                                        .stream()
                                        .map(entity -> modelMapper.map(entity, BoardDTO.class ))
                                        .toList();

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
}
