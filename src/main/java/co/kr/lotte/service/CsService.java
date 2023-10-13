package co.kr.lotte.service;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import co.kr.lotte.repository.CsRepository;
import co.kr.lotte.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsService {

    private final CsRepository csRepository;
    private final TypeRepository typeRepository;

    public List<BoardTypeEntity> findByCate(String cate){
        return typeRepository.findByCate(cate);

    }


    public void save (BoardDTO dto){
        BoardEntity entity = dto.toEntity();
        entity.setUid("d");
        entity.setCate("cate");
        log.info(entity);
        csRepository.save(entity);
    }
}
