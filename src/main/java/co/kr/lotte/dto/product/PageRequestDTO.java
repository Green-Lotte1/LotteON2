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
    @Builder.Default
    private int cate1 = 15; // 카테고리 값이 없으면 오류가 나서 디폴트로 지정
    @Builder.Default
    private int cate2 = 10;

    public Pageable getPageable() {
        if (how.equals("ASC")) {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(this.sort).ascending());
        } else {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(this.sort).descending());
        }
    }
}