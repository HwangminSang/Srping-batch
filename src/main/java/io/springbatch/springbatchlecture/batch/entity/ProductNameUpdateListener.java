package io.springbatch.springbatchlecture.batch.entity;

import io.springbatch.springbatchlecture.batch.repository.ProductHistoryRepository;
import io.springbatch.springbatchlecture.util.jpa.converter.BeanUtils;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class ProductNameUpdateListener {
    
    
    // 먼저 실행
    @PostLoad
    public void postLoad(Product product){
        product.setPreProductName(product.getName());
        
    }
    
    @PrePersist
    public void prePersist(Product product){
        
        if(product.getPreProductName() == null){
            updateProductName(product);
        }
        
    }

    @PreUpdate
    public void preUpdate(Product product) {
        if (!product.getPreProductName().equals(product.getName())) {
            updateProductName(product);
        }
    }



     void updateProductName(Product product) {
         product.setProductNameUpdateAt(LocalDateTime.now());
         ProductHistoryRepository productHistoryRepository = BeanUtils.getBean(ProductHistoryRepository.class);

         ProductHistory build = ProductHistory.builder()
                 .product(product)
                 .beforeProductName(product.getPreProductName())
                 .afterProductName(product.getName())
                 .build();
         productHistoryRepository.save(build);



     }
}
