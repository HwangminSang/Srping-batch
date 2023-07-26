package io.springbatch.springbatchlecture.batch.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_NAME_HISTORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductHistory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String beforeProductName;

    private String afterProductName;


}
