package co.kr.lotte.dto.my;

import co.kr.lotte.dto.member.MemberPointDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberPointPageResponseDTO {
        private List<MemberPointDTO> dtoList;
        private int pg;
        private int size;
        private int total;

        private int start, end;
        private boolean prev, next;

        @Builder
        public MemberPointPageResponseDTO(SearchDTO searchDTO, List<MemberPointDTO> dtoList, int total) {
            this.pg = searchDTO.getPg();
            this.size = searchDTO.getSize();
            this.total = total;
            this.dtoList = dtoList;

            this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
            this.start = this.end - 9;
            int last = (int) (Math.ceil(total / (double) size));

            this.end = Math.min(end, last);
            this.prev = this.start > 1;
            this.next = total > this.end * this.size;
        }
}