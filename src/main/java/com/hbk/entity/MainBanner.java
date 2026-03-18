package com.hbk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "main_banner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainBanner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name="link_url", length = 500)
    private String linkUrl;


}
