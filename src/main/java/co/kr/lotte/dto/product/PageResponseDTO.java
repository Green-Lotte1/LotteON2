package co.kr.lotte.dto.product;

import co.kr.lotte.dto.product.PageRequestDTO;
import co.kr.lotte.dto.product.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO {
    private List<ProductDTO> dtoList;
    private int pg;
    private int size;
    private int total;
    private int cate1;
    private int cate2;
    private String sort;
    private String how;

    private int start, end;
    private boolean prev, next;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<ProductDTO> dtoList, int total) {
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.cate1 = pageRequestDTO.getCate1();
        this.cate2 = pageRequestDTO.getCate2();
        this.sort = pageRequestDTO.getSort();
        this.how = pageRequestDTO.getHow();
        this.dtoList = dtoList;

        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;
        int last = (int) (Math.ceil(total / (double) size));

        this.end = Math.min(end, last);
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}