package io.springbatch.springbatchlecture.batch.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity {


    @CreatedDate  // 저장될시 persist
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;


    @LastModifiedDate // 업데이트 될시 persist와 updated
    @Column(name = "UPDATED_AT")

    private LocalDateTime updatedAt;
}
