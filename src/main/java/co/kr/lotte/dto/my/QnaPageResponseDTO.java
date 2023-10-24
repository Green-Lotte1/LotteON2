package co.kr.lotte.dto.my;

import co.kr.lotte.dto.cs.BoardDTO;
import co.kr.lotte.dto.cs.CsPageRequestDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QnaPageResponseDTO {

    private List<BoardDTO> dtoList;
    private String cate;
    private int pg;

    private int size;
    private int total;
    private int start, end;
    private boolean prev, next;

    @Builder
    public QnaPageResponseDTO(CsPageRequestDTO csPageRequestDTO, List<BoardDTO> dtoList, int total){
        this.cate = csPageRequestDTO.getCate();
        this.pg = csPageRequestDTO.getPg();
        this.size = csPageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.end = (int) (Math.ceil(this.pg/10.0)) * 10;
        this.start = this.end - 9;
        int last = (int) (Math.ceil(total/(double)size));

        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }


}
