package io.springbatch.springbatchlecture.scheduler;

import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Map;

import static org.quartz.JobBuilder.newJob;


/**
 *  어플리케이션 구동시적에 스케줄러를 시작
 */

public abstract class JobRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        doRun(args);
    }

    protected abstract void doRun(ApplicationArguments args);


    /**
     *  해당 시간에 맞게 trigger를 통해 스케줄러 실행
     * @param scheduleExp
     * @return
     */
    public Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    /**
     * 실행시킬 특정잡 설정
     * @param job
     * @param name
     * @param group
     * @param params
     * @return
     */

    /**
     *  크론 job
     * @param job
     * @param name
     * @param group
     * @param params
     * @return
     */
    public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        // 내부적으로 executeInternal을 크론 시간 설정에 따라 실행
        return newJob(job).withIdentity(name, group)
                .usingJobData(jobDataMap)
                .build();
    }
}
