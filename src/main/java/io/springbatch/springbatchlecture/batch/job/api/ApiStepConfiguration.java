package io.springbatch.springbatchlecture.batch.job.api;


import io.springbatch.springbatchlecture.batch.chunk.processor.ApiItemProcessor1;
import io.springbatch.springbatchlecture.batch.chunk.processor.ApiItemProcessor2;
import io.springbatch.springbatchlecture.batch.chunk.processor.ApiItemProcessor3;
import io.springbatch.springbatchlecture.batch.chunk.writer.ApiItemWriter1;
import io.springbatch.springbatchlecture.batch.chunk.writer.ApiItemWriter2;
import io.springbatch.springbatchlecture.batch.chunk.writer.ApiItemWriter3;
import io.springbatch.springbatchlecture.batch.classifier.ProcessorClassifier;
import io.springbatch.springbatchlecture.batch.classifier.WriterClassifier;
import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import io.springbatch.springbatchlecture.batch.partition.ProductPartitioner;
import io.springbatch.springbatchlecture.service.ApiService1;
import io.springbatch.springbatchlecture.service.ApiService2;
import io.springbatch.springbatchlecture.service.ApiService3;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ApiStepConfiguration { // 마스터 스텝



    private final StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;

    private int chunkSize = 10;

    private final ApiService1 apiService1;


    private final ApiService2 apiService2;

    private final ApiService3 apiService3;




    @Bean
    public Step apiMasterStep() throws Exception {
            return stepBuilderFactory.get("apiMasterStep")
                    .partitioner(apiSlaveStep().getName() , partitioner())
                    .step(apiSlaveStep())  // 복제당할 slaveStep
                    .gridSize(3) //
                    .taskExecutor(taskExecutor()) // 멀티스레드
                    .build();
    }


    @Bean
    public TaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3); // 기본 3개
        taskExecutor.setMaxPoolSize(6); // 최대 6개
        taskExecutor.setThreadNamePrefix("api-thread-");

        return taskExecutor;


    }


    @Bean
    public Step apiSlaveStep() throws Exception {

        return stepBuilderFactory.get("apiSlaveStep")
                .<ProductVO,ProductVO>chunk(chunkSize)
                // 각각의 스레드가 reader ,processor , write 를 가진다.
                // thread간의 데이터 공유는 없다. ExecutionContext를 각각 가진다.
                .reader(itemReader(null)) // 컴파일 오류 방지
                .processor(itemProcessor()) // 조건에 맞는 itemProcessor 호출
                .writer(itemWrite()) // 프로세서가 전달하는것에 맞는 write가 처리
                .build();


    }






    @Bean
    public ProductPartitioner partitioner(){

        ProductPartitioner partitioner = new ProductPartitioner();

        partitioner.setDataSource(dataSource);

        return partitioner;


    }

    @Bean
    @StepScope  // 각각의 스레드는 스스로의 StepExecutionContext를 가지고 있다.
    public ItemReader<ProductVO> itemReader(@Value("#{stepExecutionContext['product']}") ProductVO productVO) throws Exception {


        JdbcPagingItemReader<ProductVO> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(dataSource);
        reader.setPageSize(chunkSize);
        reader.setRowMapper(new BeanPropertyRowMapper<>(ProductVO.class));


        MySqlPagingQueryProvider mySqlPagingQueryProvider = new MySqlPagingQueryProvider();

        mySqlPagingQueryProvider.setSelectClause("ID , PRODUCT_ID , NAME , PRICE ,TYPE");
        mySqlPagingQueryProvider.setFromClause("FROM PRODUCT");
        mySqlPagingQueryProvider.setWhereClause("WHERE TYPE = :TYPE");
        Map<String , Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id",Order.DESCENDING);

        mySqlPagingQueryProvider.setSortKeys(sortKeys);


        reader.setParameterValues(QueryGenerator.getParameterForQuery("TYPE", productVO.getType()));
        reader.setQueryProvider(mySqlPagingQueryProvider);
        reader.afterPropertiesSet();

        return reader;
    }


    @Bean
    public ItemProcessor itemProcessor() {
        // 각각의 값에 따라 처리하기 위해 사용
        ClassifierCompositeItemProcessor<ProductVO , ApiRequestVO> processor = new ClassifierCompositeItemProcessor<>();


        // c의 조건에 따라 t를 반환
        ProcessorClassifier<ProductVO , ItemProcessor<?,? extends ApiRequestVO>> classifier = new ProcessorClassifier<>();

        Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap = new HashMap<>();
        // 상품 타입의 숫자
        processorMap.put("1" , new ApiItemProcessor1());
        processorMap.put("2" , new ApiItemProcessor2());
        processorMap.put("3" , new ApiItemProcessor3());

        classifier.setProcessorMap(processorMap);
        processor.setClassifier(classifier);


        return processor ;
    }


    @Bean
    public ItemWriter itemWrite() {


        // 각각의 값에 따라 처리하기 위해 사용
        ClassifierCompositeItemWriter<ApiRequestVO> writer = new ClassifierCompositeItemWriter<>();


        // c의 조건에 따라 t를 반환
        WriterClassifier<ApiRequestVO , ItemWriter<? super ApiRequestVO>> classifier = new WriterClassifier<>();

        Map<String , ItemWriter<ApiRequestVO>> writerMap = new HashMap<>();
        // 상품 타입의 숫자
        writerMap.put("1" , new ApiItemWriter1(apiService1));
        writerMap.put("2" , new ApiItemWriter2(apiService2));
        writerMap.put("3" , new ApiItemWriter3(apiService3));

        classifier.setWriterMap(writerMap);
        writer.setClassifier(classifier);

        return  writer;
    }



}
