package io.springbatch.springbatchlecture.scheduler;

import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;


/**
 *  잡을 만든후 쿼춰를 이용하여 30초 마다 동작하게 설정
 */
@Component
public class ApiJobRunner extends JobRunner {


    @Autowired
    private  Scheduler scheduler;

    @Override
    protected void doRun(ApplicationArguments args) {

        JobDetail jobDetail = buildJobDetail(ApiSchJob.class, "apiJob", "batch", new HashMap());
        // 크론 표현법
        Trigger trigger = buildJobTrigger("0/30 * * * * ?");

        try {
            // 해당 job을 크론 시간 설정에 따라 시작한다
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
