package co.kr.lotte.repository.cs;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.entity.cs.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CsRepository extends JpaRepository<BoardEntity, Integer> {

    public Page<BoardEntity> findByCateAndType(String cate, int type, Pageable pageable);

    @Query("SELECT b FROM BoardEntity b WHERE b.cate = :cate OR :cate = 'null'")
    public Page<BoardEntity> findByCate(String cate, Pageable pageable);

    public BoardEntity findByBno(int bno);
}
