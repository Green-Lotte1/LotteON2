package co.kr.lotte.repository.member;

import co.kr.lotte.entity.member.MemberCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Integer> {
    public List<MemberCouponEntity> findByUidAndUseYn(String uid, String useYn);
    public Long countByUidAndUseYn(String uid, String useYn);
}