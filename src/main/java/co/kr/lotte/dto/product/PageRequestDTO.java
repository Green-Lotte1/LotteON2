package co.kr.lotte.dto.product;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {
    @Builder.Default
    private int pg = 1;
    @Builder.Default
    private int size = 10;
    @Builder.Default
    private String how = "DESC";
    @Builder.Default
    private String sort = "sold";
    private int cate1;
    private int cate2;

    public Pageable getPageable() {
        if (how.equals("ASC")) {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(this.sort).ascending());
        } else {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(this.sort).descending());
        }
    }
}