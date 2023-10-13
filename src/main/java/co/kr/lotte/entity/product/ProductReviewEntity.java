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
@Table(name = "product_review")
public class ProductReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int revNo;
    private int prodNo;
    private String content;
    private String uid;
    private int rating;
    private String regip;
    private String rdate;
}