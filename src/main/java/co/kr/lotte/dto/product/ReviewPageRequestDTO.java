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
public class ReviewPageRequestDTO {
    @Builder.Default
    private int pg = 1;
    @Builder.Default
    private int size = 10;

    private int prodNo;

    public Pageable getPageable(String sort){
        return PageRequest.of(this.pg-1, this.size, Sort.by(sort).descending());
    }
}