package co.kr.lotte.repository.cs;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.entity.cs.BoardEntity;
import org.aspectj.weaver.ast.Literal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsRepository extends JpaRepository<BoardEntity, Integer> {

    public Page<BoardEntity> findByCateAndType(String cate, int type, Pageable pageable);

    @Query("SELECT b FROM BoardEntity b WHERE b.cate = :cate OR :cate = 'null'")
    public Page<BoardEntity> findByCate(String cate, Pageable pageable);

    public BoardEntity findByBno(int bno);


    public List<BoardEntity> findTop10ByType(int type);

    //public List<BoardEntity> findByGroup(String group);

    public List<BoardEntity> findByGroupAndTypeGreaterThanOrderByRdateDescBnoDesc(String group, int type, Pageable pageable);

    public List<BoardEntity> findByGroupAndTypeLessThanOrderByRdateDescBnoDesc(String group, int type, Pageable pageable);

}

