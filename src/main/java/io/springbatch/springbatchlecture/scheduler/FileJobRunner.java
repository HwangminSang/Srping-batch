package io.springbatch.springbatchlecture.scheduler;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.quartz.JobBuilder.newJob;

@Component
public class FileJobRunner extends JobRunner {

    @Autowired
    private Scheduler scheduler;

    /**
     * 스프링부트 구동시작에 파라미터로 넣은 값이 여기로 온다
     * 해당 날짜의 문서를 읽기 위해서 사용
     * @param args
     */
    @Override
    protected void doRun(ApplicationArguments args) {

        String[] sourceArgs = args.getSourceArgs();// 전달 받은 파라미터를 찾기위해
        JobDetail jobDetail = buildJobDetail(FileSchJob.class, "fileJob", "batch", new HashMap());
        Trigger trigger = buildJobTrigger("0/50 * * * * ?");
        // 예외처리 필요 ( 데이터 없을때 )
        jobDetail.getJobDataMap().put("requestDate", sourceArgs[0]);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
