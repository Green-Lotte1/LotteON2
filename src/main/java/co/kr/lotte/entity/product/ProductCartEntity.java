package co.kr.lotte.entity.product;

import co.kr.lotte.dto.product.ProductCartDTO;
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
@Table(name = "product_cart")
public class ProductCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartNo;
    private String uid;
    private int prodNo;
    private int count;
    @CreationTimestamp
    private LocalDateTime rdate;

    public ProductCartDTO toDTO() {
        return ProductCartDTO.builder()
                .cartNo(cartNo)
                .uid(uid)
                .prodNo(prodNo)
                .count(count)
                .rdate(rdate)
                .build();
    }
}