package co.kr.lotte.dto.cs;

import co.kr.lotte.entity.cs.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private int bno;
    private String uid;
    private String group;
    private String cate;
    private int type;
    private String title;
    private String content;
    private LocalDateTime rdate;

    private String typeName;
    private String cateName;


    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .uid(uid)
                .group(group)
                .cate(cate)
                .type(type)
                .title(title)
                .content(content)
                .rdate(rdate)
                .build();
    }
}
