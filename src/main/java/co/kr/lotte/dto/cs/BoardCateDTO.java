package co.kr.lotte.dto.cs;

import co.kr.lotte.entity.cs.BoardCateEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardCateDTO {
    private String cate;
    private String cateName;

    // 추가필드
    private List<BoardTypeDTO> type;

    public BoardCateEntity toEntity(){
        return BoardCateEntity.builder()
                .cate(cate)
                .cateName(cateName)
                .build();
    }
}
