package io.springbatch.springbatchlecture.batch.job.file;

import io.springbatch.springbatchlecture.batch.chunk.processor.*;
import io.springbatch.springbatchlecture.batch.entity.Product;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;


    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                .start(fileStep1())
                .build();
    }


    /**
     * chunk 기반.
     * @return
     */
    @Bean
    public Step fileStep1() {
        return stepBuilderFactory.get("fileStep1")
                .<ProductVO, Product>chunk(10)
                .reader(fileItemReader(null)) // 런타임중
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    @StepScope  // 프록시 객체를 만듬. 런타임중에 실제 객체 호출시 value에 바인딩 된다.
    public FlatFileItemReader<ProductVO> fileItemReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        return new FlatFileItemReaderBuilder<ProductVO>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requestDate +".csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>()) // 해당 파일을 읽어와 필드를 만든다.
                .targetType(ProductVO.class) // 객체로 바인딩
                .linesToSkip(1) // 첫번쨰 라인은 넘긴다/
                .delimited().delimiter(",") // 구분자는 ,
                .names("productId","name","price","type")  // 이름으로 맵핑
                .build();
    }

    @Bean
    public ItemProcessor<ProductVO, Product> fileItemProcessor() {
        return new FileItemProcessor();
    }

    @Bean
    public JpaItemWriter<Product> fileItemWriter() {
        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
}
