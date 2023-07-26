package io.springbatch.springbatchlecture.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 *  스케줄러에서 구현한 job을 상속받아서 처리 < 배치 job과는 다름 >
 *   내부적으로 스케줄러가 execute 메소드 호출
 */
@Component
@Slf4j
public class ApiSchJob extends QuartzJobBean{

	@Autowired
	private Job apiJob;  // 실행시킬 job

	@Autowired
	private JobLauncher jobLauncher;  // 잡 실행


    /**
     * QuartzJobBean 의 추상메서드로 실제 로직 구현부
	 * 여기서 batch 실행 로직을 만들어야한다.
     * @param context
     * @throws JobExecutionException
     */
    @SneakyThrows
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		// 여러번 잡을 실행시키기위해 파라미터를 매번 다르게 설정
		JobParameters jobParameters = new JobParametersBuilder()
									.addLong("id", new Date().getTime())
									.toJobParameters();
		// 크론에 따라 시작.
		jobLauncher.run(apiJob, jobParameters);
	}
}
