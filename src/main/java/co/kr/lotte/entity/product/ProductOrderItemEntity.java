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
@Table(name = "product_order_item")
public class ProductOrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private int ordNo;
    private int prodNo;
    private int count;
}