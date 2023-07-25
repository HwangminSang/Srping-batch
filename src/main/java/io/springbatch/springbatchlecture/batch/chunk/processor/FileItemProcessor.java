package io.springbatch.springbatchlecture.batch.chunk.processor;

import io.springbatch.springbatchlecture.batch.entity.Product;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

import java.util.UUID;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {


    // 받아온 V0-> PRODUCT엔티티로
    @Override
    public Product process(ProductVO item) throws Exception {

//        ModelMapper modelMapper = new ModelMapper();
//
//
//        Product product = modelMapper.map(item, Product.class);

        String productId = item.getProductId().concat(UUID.randomUUID().toString());

        Product product = Product.builder()
                .price(item.getPrice())
                .type(item.getType())
                .name(item.getName())
                .productId(productId)
                .build();


        return product;
    }
}
