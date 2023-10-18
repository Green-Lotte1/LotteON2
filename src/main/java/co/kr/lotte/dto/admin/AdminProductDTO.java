package co.kr.lotte.dto.admin;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductDTO {
    private int no;
    private int cate1;
    private int cate2;
    private String c2Name;
}
