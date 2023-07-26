package io.springbatch.springbatchlecture.test;

import io.springbatch.springbatchlecture.batch.entity.Product;
import io.springbatch.springbatchlecture.batch.repository.ProductHistoryRepository;
import io.springbatch.springbatchlecture.batch.repository.ProductRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductNameTestTest {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductHistoryRepository productHistoryRepository;
    @Test
    public void listenrTest(){

        Product product = Product.builder()
                .productId("123213213")
                .name("상품이름1")
                .type("1")
                .price(11111)
                .build();

        productRepository.save(product);

        Product ssss = productRepository.findByName("상품이름1");

        ssss.setName("새로운이름");
        ssss.setPrice(22222);
        productRepository.save(ssss);

        productHistoryRepository.findAll().forEach(System.out::println);

    }

    @After
    public void cleanup() {
        productHistoryRepository.deleteAll();
    }

}