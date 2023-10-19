package co.kr.lotte.dto.cs;

import co.kr.lotte.entity.cs.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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
    private int file;
    private String status;
    private String reply;
    private LocalDateTime rdate;
    // 추가필드
    private MultipartFile fname;
    private String typeName;
    private String cateName;
    private int index;

    private List<BoardFileDTO> fileDTOList;




    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .uid(uid)
                .group(group)
                .cate(cate)
                .type(type)
                .title(title)
                .content(content)
                .file(file)
                .status(status)
                .reply(reply)
                .rdate(rdate)
                .build();
    }
}
