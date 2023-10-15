package co.kr.lotte.repository.cs;

import co.kr.lotte.entity.cs.BoardCateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCateRepository extends JpaRepository<BoardCateEntity, String> {


}
