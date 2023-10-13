package co.kr.lotte.entity.product;

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
}