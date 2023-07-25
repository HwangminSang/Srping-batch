package io.springbatch.springbatchlecture.batch.job.api;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SendChildJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Step apiMasterStep; // 멀티스레드
    private final JobLauncher jobLauncher;  // jobStep을 이용하기 위해 필요

    @Bean
    public Step jobStep() throws Exception {
        return stepBuilderFactory.get("jobStep")
                .job(childJob())
                .launcher(jobLauncher)
                .build();
    }

    @Bean
    public Job childJob() throws Exception {
        return jobBuilderFactory.get("childJob")
                .start(apiMasterStep) // 마스터 스텝 실행시 슬레이브 스텝을 복사하여 시작 ( 청크기반 멀티스레드)
                .build();
    }
}