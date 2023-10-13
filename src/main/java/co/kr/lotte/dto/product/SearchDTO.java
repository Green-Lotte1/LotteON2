package co.kr.lotte.dto.product;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO { // 상품 목록에서 검색 조건을 위해서 만든 폼
    private int prodNo;
    private int cate1;
    private int cate2;
}