package io.springbatch.springbatchlecture.batch.entity;


import io.springbatch.springbatchlecture.util.jpa.converter.BooleanToYNConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "API_RESULT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ApiResult extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "SERVICE_NAME")
    private String serviceName;


    @Column(name ="STATUS")
    @Convert(converter =  BooleanToYNConverter.class)
    private boolean status;
}
