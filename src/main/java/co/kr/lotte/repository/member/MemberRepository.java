package co.kr.lotte.repository.member;

import co.kr.lotte.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MemberEntity, String> {

    int countByUid(String uid);
}
