package co.kr.lotte.entity.cs;

import co.kr.lotte.dto.cs.BoardTypeDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cs_boardType")
public class BoardTypeEntity {
    @Id
    private int no;
    private String cate;
    private int type;
    private String typeName;

    public BoardTypeDTO toDTO(){
        return BoardTypeDTO.builder()
                .no(no)
                .cate(cate)
                .type(type)
                .typeName(typeName)
                .build();
    }
}
