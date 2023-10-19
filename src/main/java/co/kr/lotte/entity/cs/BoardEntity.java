package co.kr.lotte.entity.cs;

import co.kr.lotte.dto.cs.BoardDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "cs_board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    private String uid;
    @Column(name = "\"group\"")
    private String group;
    private String cate;
    private int type;
    private String title;
    private String content;
    @Builder.Default
    private Integer file = -1;
    private String status;
    private String reply;
    @CreationTimestamp
    private LocalDateTime rdate;


    public BoardDTO toDTO(){
        return BoardDTO.builder()
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
