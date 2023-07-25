package io.springbatch.springbatchlecture.batch.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private String name;


    @Column(name ="PRICE")
    private int price;


    @Column(name ="TYPE")
    private String type;

}
