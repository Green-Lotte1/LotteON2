package co.kr.lotte.entity.product;

import co.kr.lotte.dto.product.ProductCate2DTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_cate2")
public class ProductCate2Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private int cate1;
    private int cate2;
    private String c2Name;

    public ProductCate2DTO toDTO() {
        return ProductCate2DTO.builder()
                .no(no)
                .cate1(cate1)
                .cate2(cate2)
                .c2Name(c2Name)
                .build();
    }
}