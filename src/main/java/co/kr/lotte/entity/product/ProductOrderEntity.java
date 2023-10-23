package co.kr.lotte.entity.product;

import co.kr.lotte.dto.product.ProductOrderDTO;
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
@Table(name = "product_order")
public class ProductOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ordNo;
    private String ordUid;
    private int ordCount;
    private int ordPrice;
    private int ordDiscount;
    private int ordDelivery;
    private int savePoint;
    private int usedPoint;
    private int ordTotPrice;
    private String recipName;
    private String recipHp;
    private String recipZip;
    private String recipAddr1;
    private String recipAddr2;
    private int ordPayment;
    private int ordComplete;
    @CreationTimestamp
    private LocalDateTime ordDate;
    private String ordUser;

    public ProductOrderDTO toDTO() {
        return ProductOrderDTO.builder()
                .ordNo(ordNo)
                .ordUid(ordUid)
                .ordCount(ordCount)
                .ordPrice(ordPrice)
                .ordDiscount(ordDiscount)
                .ordDelivery(ordDelivery)
                .savePoint(savePoint)
                .usedPoint(usedPoint)
                .ordTotPrice(ordTotPrice)
                .recipName(recipName)
                .recipHp(recipHp)
                .recipZip(recipZip)
                .recipAddr1(recipAddr1)
                .recipAddr2(recipAddr2)
                .ordPayment(ordPayment)
                .ordComplete(ordComplete)
                .ordDate(ordDate.toString())
                .ordUser(ordUser)
                .build();
    }
}