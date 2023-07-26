package io.springbatch.springbatchlecture.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class FileSchJob extends QuartzJobBean{


	//빈 이름으로 바인딩 해 준다고 보시면 됩니다
	@Autowired
	private Job fileJob; // 실행시킬 job

	@Autowired
	private JobLauncher jobLauncher;  // 실행

	@Autowired
	private JobExplorer jobExplorer;  // jobRepository의 read-only 기능만 있음.

	@SneakyThrows
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		// 프로그램 인수로 받아온 값을 꺼넨다.
		String requestDate = (String)context.getJobDetail().getJobDataMap().get("requestDate");

		// 받은날짜 , 중복 x
		JobParameters jobParameters = new JobParametersBuilder()
									.addLong("id", new Date().getTime())
									.addString("requestDate", requestDate)
									.toJobParameters();


		/**
		 * 받아온 날짜파리미터로 이미 실행 시킨적이 있는 job인지 체크
		 * jobExploer 사용
		 * job_Execution_id를 부모로 가진다.  < BATCH_JOB_EXECUTION_PARAMS 참조
		 */

		// 똑같은 이름으로 실행된 JOB의 갯수를 가져온다
		int jobInstanceCount = jobExplorer.getJobInstanceCount(fileJob.getName());
		//  똑같은 이름으로 실행된 모든 JOBINSTANCE를 가져온다
		List<JobInstance> jobInstances = jobExplorer.getJobInstances(fileJob.getName(), 0, jobInstanceCount);

		// 배치 실행전 날짜 체크
		// 실행된적이 없는경우 바로 실행

		if(jobInstances.size() > 0) {

			for(JobInstance jobInstance : jobInstances){
				// 해당 JOB에 속해있는 여러개의 Execution을 가져올수있다.
				List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
				Long checkJob = jobExecutions.stream().filter(jobExecution ->
						//BATCH_JOB_EXECUTION_PARAMS을 가져온다
								jobExecution.getJobParameters().getString("requestDate").equals(requestDate))
						.count();
				if (checkJob != null || checkJob != 0) {
					throw new JobExecutionException(requestDate + " already exists");
				}
			}
		}

		jobLauncher.run(fileJob, jobParameters);
	}

}
