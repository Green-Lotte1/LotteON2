package co.kr.lotte.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTermsDTO {
    private String terms;
    private String privacy;
    private String finance;
    private String location;
    private String tax;
}