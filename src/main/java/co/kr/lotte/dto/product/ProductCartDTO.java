package co.kr.lotte.dto.product;

import co.kr.lotte.entity.product.ProductCartEntity;
import lombok.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

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
    private LocalDateTime rdate;
    private ProductDTO product;

    public ProductCartEntity toEntity() {
        return ProductCartEntity.builder()
                .uid(uid)
                .prodNo(prodNo)
                .count(count)
                .build();
    }

    public int getTotal() {
        return (int)(((double) count) * product.getDisPrice());
    }

    public String getTotalWithComma() {
        int total = (int)(((double) count) * product.getDisPrice());
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(total);
    }
}