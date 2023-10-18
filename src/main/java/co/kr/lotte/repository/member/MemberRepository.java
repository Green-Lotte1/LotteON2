package co.kr.lotte.repository.member;

import co.kr.lotte.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    int countByUid(String uid);
    int countByEmail(String email);
    int countByHp(String hp);
    int countByCompany(String company);
    int countByBizRegNum(String biRegNum);
    int countByComRegNum(String comRegNum);
    int countTel(String tel);
    int countByFax(String fax);
}
