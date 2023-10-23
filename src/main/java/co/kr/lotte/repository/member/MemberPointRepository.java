package co.kr.lotte.repository.member;

import co.kr.lotte.entity.member.MemberPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPointRepository extends JpaRepository<MemberPointEntity, Integer> {

}