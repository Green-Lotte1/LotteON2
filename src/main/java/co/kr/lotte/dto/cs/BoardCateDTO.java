package co.kr.lotte.dto.cs;

import co.kr.lotte.entity.cs.BoardCateEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardCateDTO {
    private String cate;
    private String cateName;


    public BoardCateEntity toEntity(){
        return BoardCateEntity.builder()
                .cate(cate)
                .cateName(cateName)
                .build();
    }
}
