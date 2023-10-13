package co.kr.lotte.dto.product;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartDTO {
    private int cartNo;
    private String uid;
    private int prodNo;
    private int count;
    private String rdate;
}