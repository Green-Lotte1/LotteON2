package co.kr.lotte.dto.product;

import lombok.*;

import java.text.DecimalFormat;

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
    private int cartNo;
    private int count;
    private String cart;
    private ProductDTO product;

    public int getTotal() {
        return (int)(((double) count) * product.getDisPrice());
    }

    public String getTotalWithComma() {
        int total = (int)(((double) count) * product.getDisPrice());
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(total);
    }
}