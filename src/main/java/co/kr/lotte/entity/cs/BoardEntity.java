package co.kr.lotte.entity.cs;

import co.kr.lotte.dto.cs.BoardDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
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

    @CreationTimestamp
    private LocalDateTime rdate;


    public BoardDTO toDTO(){
        return BoardDTO.builder()
                .bno(bno)
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
