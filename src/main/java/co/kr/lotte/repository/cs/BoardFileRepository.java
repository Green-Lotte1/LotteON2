package co.kr.lotte.repository.cs;

import co.kr.lotte.entity.cs.BoardCateEntity;
import co.kr.lotte.entity.cs.BoardEntity;
import co.kr.lotte.entity.cs.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Integer> {

    public List<BoardFileEntity> findByBno(int bno);
    public BoardFileEntity findSfileByFno(int fno);

    @Query("SELECT bf FROM BoardFileEntity bf WHERE bf.bno = :bno")
    BoardFileEntity findOneByBno(int bno);
}

