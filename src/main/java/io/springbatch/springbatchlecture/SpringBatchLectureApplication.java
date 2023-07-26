package io.springbatch.springbatchlecture;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableBatchProcessing
@EnableJpaAuditing
public class SpringBatchLectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchLectureApplication.class, args);
    }

}
