package io.springbatch.springbatchlecture.batch.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(value = ProductNameUpdateListener.class)
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String type;

    @Column(name = "PRODUCT_ID")
    private String productId;


    @Column(name = "PRODUCT_NAME_UPDATED_AT")
    private LocalDateTime productNameUpdateAt;



    // db에 생성되지 않도록   , 상품의 이전 이름
    @Transient
    private String preProductName;

    @Column(name = "CLASIIFI_CODE")
    private String clasiifiCode;

    //회원 엔티티를 저장하면 회원 테이블의 FULLNAME 컬럼에 firstName + lastName의 결과가 저장된다


    @Access(AccessType.PROPERTY)
    public String getClasiifiCode(){
        return name + "_" + type + "_" + productId;
    }


    @Builder
    public Product(String name ,int price , String type ,String productId){
        this.name = name;
        this.price = price;
        this.type = type;
        this.productId = productId;
    }
}
