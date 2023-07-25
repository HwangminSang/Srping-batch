package io.springbatch.springbatchlecture.batch.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String type;

    @Column(name = "PRODUCT_ID")
    private String productId;
}
