package co.kr.lotte.repository;

import co.kr.lotte.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    int countByUid(String uid);
}
