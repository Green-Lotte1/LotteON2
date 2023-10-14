package co.kr.lotte.dto.product;

import co.kr.lotte.entity.product.ProductCartEntity;
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

    public ProductCartEntity toEntity() {
        return ProductCartEntity.builder()
                .uid(uid)
                .prodNo(prodNo)
                .count(count)
                .build();
    }
}