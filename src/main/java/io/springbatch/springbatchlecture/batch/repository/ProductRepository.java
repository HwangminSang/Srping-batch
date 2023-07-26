package io.springbatch.springbatchlecture.batch.repository;

import io.springbatch.springbatchlecture.batch.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
    Product findByName(String name);
}
