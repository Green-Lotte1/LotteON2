package co.kr.lotte.entity.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="member_coupon")
public class MemberCouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponSeq;
    private String uid;
    private String couponName;
    private String discountType;
    private int discountLimit;
    private int discountMoney;
    private int discountPercent;
    @Builder.Default
    private String useYn = "Y";
    @CreationTimestamp
    private LocalDateTime rdate;
    private LocalDateTime expireDate;
}