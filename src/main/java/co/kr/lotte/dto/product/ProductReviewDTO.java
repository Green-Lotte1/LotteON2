package co.kr.lotte.dto.product;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDTO {
    private int revNo;
    private int prodNo;
    private String content;
    private String uid;
    private int rating;
    private String regip;
    private String rdate;
}