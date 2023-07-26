package io.springbatch.springbatchlecture.batch.repository;

import io.springbatch.springbatchlecture.batch.entity.ApiResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiResultRepository extends JpaRepository<ApiResult , Long> {
}
