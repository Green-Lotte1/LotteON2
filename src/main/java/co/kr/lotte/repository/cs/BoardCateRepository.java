package co.kr.lotte.repository.cs;

import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCateRepository extends JpaRepository<BoardCateEntity, String> {

    public List<BoardCateEntity> findByCate(String cate);
}
