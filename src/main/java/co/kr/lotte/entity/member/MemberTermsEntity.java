package co.kr.lotte.entity.member;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="member_terms")
public class MemberTermsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String terms; // 일반회원 이용약관
    private String privacy; // 개인정보(공통)
    private String finance; // 전자금융(공통)
    private String location; // 일반회원 위치정보
    private String tax; // 판매자 이용약관
}