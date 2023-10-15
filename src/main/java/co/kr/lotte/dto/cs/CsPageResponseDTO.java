package co.kr.lotte.dto.cs;

import co.kr.lotte.dto.product.PageRequestDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CsPageResponseDTO {

    private List<BoardDTO> dtoList;
    private String cate;
    private int pg;

    private int size;
    private int total;
    private int start, end;
    private boolean prev, next;

    @Builder
    public CsPageResponseDTO(CsPageRequestDTO csPageRequestDTO, List<BoardDTO> dtoList, int total){
        this.cate = csPageRequestDTO.getCate();
        this.pg = csPageRequestDTO.getPg();
        this.size = csPageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.end = (int) (Math.ceil(this.pg)/10.0) * 10;
        this.start = this.end - 9;
        int last = (int) (Math.ceil(total/(double)size));

        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }


}
