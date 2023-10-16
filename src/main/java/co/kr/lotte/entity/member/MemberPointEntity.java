package co.kr.lotte.entity.member;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="member_point")
public class MemberPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointNo;
    private String uid;
    private int ordNo;
    private int point;
    private LocalDateTime pointDate;
}
