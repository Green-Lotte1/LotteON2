package co.kr.lotte.repository;

import co.kr.lotte.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MemberEntity, String> {

    int countByUid(String uid);
}
