package com.hbk.entity;

import jakarta.persistence.*;
import lombok.*;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "brands",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_brands_name", columnNames = "name"),
                @UniqueConstraint(name = "uk_brands_slug", columnNames = "slug")
        })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Brand extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 60)
    private String slug;

    @Column(name = "logo_url", length = 300)
    private String logoUrl;


    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Product> products = new ArrayList<>();




}