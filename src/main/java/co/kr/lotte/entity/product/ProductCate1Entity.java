package co.kr.lotte.entity.product;

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
}