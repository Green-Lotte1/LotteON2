package co.kr.lotte.dto.product;

import co.kr.lotte.entity.product.ProductReviewEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime rdate;

    private ProductDTO product;

    public ProductReviewEntity toEntity() {
        return ProductReviewEntity.builder()
                .prodNo(prodNo)
                .content(content)
                .uid(uid)
                .rating(rating)
                .build();
    }
}