package co.kr.lotte.repository.member;

import co.kr.lotte.entity.member.MemberPointEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MemberPointRepository extends JpaRepository<MemberPointEntity, Integer> {
    public Page<MemberPointEntity> findByUidAndPointDateBetween(String uid, LocalDateTime begin, LocalDateTime end, Pageable pageable);
    public List<MemberPointEntity> findByUid(String uid);

}