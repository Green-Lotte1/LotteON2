package co.kr.lotte.entity.product;

import co.kr.lotte.dto.product.ProductCate1DTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_cate1")
public class ProductCate1Entity {
    @Id
    private int cate1;
    private String c1Name;
    private String css;

    public ProductCate1DTO toDTO() {
        return ProductCate1DTO.builder()
                .cate1(cate1)
                .c1Name(c1Name)
                .css(css)
                .build();
    }
}