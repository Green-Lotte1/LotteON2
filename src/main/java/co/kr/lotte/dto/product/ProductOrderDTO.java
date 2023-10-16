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
    private String ordPaymentName;

    public void setOrdPaymentName () {
        if (ordPayment == 1) {
            this.ordPaymentName = "신용카드";
        }
        else if (ordPayment == 2) {
            this.ordPaymentName = "체크카드";
        }
        else if (ordPayment == 3) {
            this.ordPaymentName = "계좌이체";
        }
         else if (ordPayment == 4) {
            this.ordPaymentName = "무통장 입금";
        }
         else if (ordPayment == 5) {
            this.ordPaymentName = "휴대폰결제";
        }
         else if (ordPayment == 6) {
            this.ordPaymentName = "카카오페이";
        }
    }

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
                .ordUser(ordUser)
                .build();
    }
}