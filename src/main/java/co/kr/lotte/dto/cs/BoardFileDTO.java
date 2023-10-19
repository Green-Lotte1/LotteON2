package co.kr.lotte.dto.cs;

import co.kr.lotte.entity.cs.BoardFileEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardFileDTO {
    private int fno;
    private int bno;
    private String ofile;
    private String sfile;
    private int download;
    private LocalDateTime rdate;

    public BoardFileEntity toEntity(){
        return BoardFileEntity.builder()
                .fno(fno)
                .bno(bno)
                .ofile(ofile)
                .sfile(sfile)
                .download(download)
                .rdate(rdate)
                .build();
    }
}
