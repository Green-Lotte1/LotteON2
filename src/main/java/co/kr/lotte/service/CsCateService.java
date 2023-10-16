package co.kr.lotte.service;


import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.BoardTypeDTO;
import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.repository.cs.BoardCateRepository;
import co.kr.lotte.repository.cs.BoardTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class CsCateService {

    private final BoardCateRepository boardCateRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final ModelMapper modelMapper;


    public List<BoardCateEntity> getCate(){
        List<BoardCateEntity> boardCateEntities = boardCateRepository.findAll();
        return boardCateEntities;
    }


    public List<BoardTypeEntity> findByCate(String cate){
        return boardTypeRepository.findByCate(cate);

    }
    public List<BoardTypeDTO> findByCateTypeDTOS(String cate){
        return boardTypeRepository.findByCate(cate).stream()
                .map(entity -> modelMapper.map(entity, BoardTypeDTO.class ))
                .toList();

    }


}
