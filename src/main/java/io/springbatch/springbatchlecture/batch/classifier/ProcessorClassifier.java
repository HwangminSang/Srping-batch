package io.springbatch.springbatchlecture.batch.classifier;

import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;


// 조건에 따라 processor 실행을 위해 만듬
public class ProcessorClassifier<C,T> implements Classifier<C, T> {

    private Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap = new HashMap<>();




    /// C 에는 조건. ProductVo
    // T ItemProcessor 조건에 맞는 놈
    @Override
    public T classify(C classifiable) {

        // 1 , 2 , 3 < 현재 상품의 타입 종류 >
        return (T) processorMap.get(((ProductVO)classifiable).getType());
    }

    public void setProcessorMap(Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap) {
        this.processorMap = processorMap;
    }
}