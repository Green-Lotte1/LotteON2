package co.kr.lotte.dto.member;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTermsDTO {
    private int id;
    private String terms;
    private String privacy;
    private String finance;
    private String location;
    private String tax;
}