package co.kr.lotte.entity.cs;

import co.kr.lotte.dto.cs.BoardCateDTO;
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
@Table(name = "cs_boardCate")
public class BoardCateEntity {

    @Id
    private String cate;
    private String cateName;


    public BoardCateDTO toDTO(){
        return BoardCateDTO.builder()
                .cate(cate)
                .cateName(cateName)
                .build();
    }

}
