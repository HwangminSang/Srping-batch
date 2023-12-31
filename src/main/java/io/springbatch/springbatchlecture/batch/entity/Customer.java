package io.springbatch.springbatchlecture.batch.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "NAME")
    private String name;


    @Column(name ="PRICE")
    private int price;


    @Column(name ="TYPE")
    private String type;

}
