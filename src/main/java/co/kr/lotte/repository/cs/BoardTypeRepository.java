package co.kr.lotte.repository.cs;

import co.kr.lotte.entity.cs.BoardTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardTypeRepository extends JpaRepository<BoardTypeEntity, Integer> {

    public List<BoardTypeEntity> findByCate(String cate);

}
