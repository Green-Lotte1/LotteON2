package co.kr.lotte.repository.member;

import co.kr.lotte.entity.member.MemberTermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<MemberTermsEntity, Integer> {
}
