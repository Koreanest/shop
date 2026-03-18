package com.hbk.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="footer_text")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FooterText extends BaseTimeEntity{

    @Id
    private Long id; // 항상 1로 고정 추천

    @Lob
    @Column(name = "paragraph1")
    private String paragraph1;

    @Lob
    @Column(name = "paragraph2")
    private String paragraph2;
}