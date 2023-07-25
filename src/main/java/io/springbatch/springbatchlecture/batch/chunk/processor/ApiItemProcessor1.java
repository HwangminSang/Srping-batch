package io.springbatch.springbatchlecture.batch.chunk.processor;

import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


// 각각의 스레드가 독립적으로 실행되도록 구성
public class ApiItemProcessor1 implements ItemProcessor<ProductVO, ApiRequestVO> {

    @Override
    public ApiRequestVO process(ProductVO productVO) throws Exception {

        return ApiRequestVO.builder()
                .id(productVO.getId())
                .productVO(productVO)
                .build();
    }
}
