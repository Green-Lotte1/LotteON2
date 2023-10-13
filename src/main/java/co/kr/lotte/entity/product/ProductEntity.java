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
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodNo;
    private int prodCate1;
    private int prodCate2;
    private String prodName;
    private String descript;
    private String company;
    private String seller;
    private int price;
    private int discount;
    private int point;
    private int stock;
    private int sold;
    private int delivery;
    private int hit;
    private int score;
    private int review;
    private String thumb1;
    private String thumb2;
    private String thumb3;
    private String detail;
    private String status;
    private String duty;
    private String receipt;
    private String bizType;
    private String origin;
    private String ip;
    private String rdate;
    private String deleteYn;
    private int etc1;
    private int etc2;
    private String etc3;
    private String etc4;
    private String etc5;
}