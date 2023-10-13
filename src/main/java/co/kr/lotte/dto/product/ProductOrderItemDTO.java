package co.kr.lotte.dto.product;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderItemDTO {
    private int no;
    private int ordNo;
    private int prodNo;
    private int count;
}