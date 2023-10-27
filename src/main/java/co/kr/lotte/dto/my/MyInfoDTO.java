package co.kr.lotte.dto.my;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MyInfoDTO {
    private int myPoint;
    private int couponCount;
    private int qnaCount;
    private int orderCount;
}