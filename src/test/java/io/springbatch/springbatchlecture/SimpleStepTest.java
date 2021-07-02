/*
package io.springbatch.springbatchlecture;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes={SimpleJobConfiguration.class, TestBatchConfig.class})
public class SimpleStepTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private DataSource dataSource;

    @Test
    public void simple_job_테스트() throws Exception {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("delete from customer2");
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "20020101")
                .addLong("date", new Date().getTime())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
    }
}*/