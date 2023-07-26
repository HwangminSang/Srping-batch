package io.springbatch.springbatchlecture.batch.repository;

import io.springbatch.springbatchlecture.batch.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
}
