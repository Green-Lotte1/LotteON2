package co.kr.lotte.dto.product;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCate1DTO {
    private int cate1;
    private String c1Name;
    private String css;

    // 추가필드
    private List<ProductCate2DTO> cate2s;
}