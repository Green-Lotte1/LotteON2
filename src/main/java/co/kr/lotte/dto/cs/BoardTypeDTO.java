package co.kr.lotte.dto.cs;

import co.kr.lotte.entity.cs.BoardTypeEntity;
import lombok.*;

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

    public BoardTypeEntity toEntity(){
        return BoardTypeEntity.builder()
                .no(no)
                .cate(cate)
                .type(type)
                .typeName(typeName)
                .build();
    }
}
