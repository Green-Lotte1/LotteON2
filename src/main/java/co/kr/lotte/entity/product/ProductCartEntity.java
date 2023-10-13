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
@Table(name = "product_cart")
public class ProductCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartNo;
    private String uid;
    private int prodNo;
    private int count;
    private String rdate;
}