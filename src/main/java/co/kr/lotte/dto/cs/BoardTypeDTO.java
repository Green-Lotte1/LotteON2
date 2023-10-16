package co.kr.lotte.dto.cs;

import co.kr.lotte.entity.cs.BoardTypeEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardTypeDTO {

    private int no;
    private String cate;
    private int type;
    private String typeName;
    private List<BoardDTO> boards;

    public BoardTypeEntity toEntity(){
        return BoardTypeEntity.builder()
                .no(no)
                .cate(cate)
                .type(type)
                .typeName(typeName)
                .build();
    }
}
