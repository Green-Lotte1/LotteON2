package co.kr.lotte.dto.member;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPointDTO {
    private int pointNo;
    private String uid;
    private int ordNo;
    private int point;
    private LocalDateTime pointDate;
}
