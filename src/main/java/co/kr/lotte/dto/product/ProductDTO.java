package co.kr.lotte.dto.product;

import jakarta.persistence.Table;
import lombok.*;

import java.text.DecimalFormat;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
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

    public int getDisPrice() {
        return (int) Math.floor(((double) price / 1000) * (100 - discount)) * 10;
    }

    public String getPriceWithComma() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(price);
    }

    public String getDisPriceWithComma() {
        double disPrice = Math.floor(((double) price / 1000) * (100 - discount)) * 10;
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(disPrice);
    }

    public int getDisedPrice() {
        return price - getDisPrice();
    }

    public String getDisedPriceWithComma() {
        int disedPrice = price - getDisPrice();
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(disedPrice);
    }

    public String getDeliveryWithComma() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(delivery);
    }
}