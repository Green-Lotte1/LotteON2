package co.kr.lotte.dto.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberCouponDTO {
    private int couponSeq;
    private String uid;
    private String couponName;
    private String discountType;
    private int discountLimit;
    private int discountMoney;
    private int discountPercent;
    private String useYn;
    private LocalDateTime rdate;
    private LocalDateTime expireDate;

    private String useYnString;

    public void changeUseYnString() {
        this.useYnString = this.useYn.equals("Y") ? "사용가능" : "사용완료";
    }

    public String getDiscountLimitWithComma() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(discountLimit);
    }

    public String getDiscountMoneyWithComma() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(discountMoney);
    }

    public String getRdateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return rdate.format(formatter);
    }

    public String getExpireDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return expireDate.format(formatter);
    }
}