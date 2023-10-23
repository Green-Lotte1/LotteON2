package co.kr.lotte.dto.my;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SearchDTO {
    private String begin;
    private String end;
    @Builder.Default
    private int pg = 1;
    @Builder.Default
    private int size = 10;

    public Pageable getPageable() {
        return PageRequest.of(this.pg - 1, this.size);
    }
}