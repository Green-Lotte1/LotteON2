package co.kr.lotte.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="member_terms")
public class MemberTermsEntity {
    private String terms;
    private String privacy;
    private String finance;
    private String location;
    private String tax;
}