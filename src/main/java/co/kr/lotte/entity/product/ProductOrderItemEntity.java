package co.kr.lotte.entity.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    private String ordUid;
    private int ordNo;
    private int prodNo;
    private int count;
    @CreationTimestamp
    private LocalDateTime ordDate;
}