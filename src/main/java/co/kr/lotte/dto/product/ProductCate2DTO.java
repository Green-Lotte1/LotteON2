package co.kr.lotte.dto.product;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCate2DTO {
    private int no;
    private int cate1;
    private int cate2;
    private String c2Name;
}