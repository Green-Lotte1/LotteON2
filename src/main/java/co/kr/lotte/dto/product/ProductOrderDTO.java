package co.kr.lotte.dto.product;

import co.kr.lotte.entity.product.ProductOrderEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDTO {
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
    private String ordDate;
    private String ordUser;

    public ProductOrderEntity toEntity() {
        return ProductOrderEntity.builder()
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
                .ordDate(ordDate)
                .build();
    }
}