package cn.edu.cuit.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 调度
 * @author guanghua.ying
 * @date 2017年7月10日 下午5:40:43
 */
public class Test {

	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
				.withIdentity("job1", "group1")
				.build();
		
		jobDetail.getJobDataMap().put("task", "1234");
		
		Trigger trigger1 = TriggerBuilder.newTrigger()
				.withIdentity("trigger1", "group1")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(5)//每5秒触发一次
						.repeatForever())
				.build();
		
		Trigger trigger2 = TriggerBuilder.newTrigger()
				.withIdentity("simpleTrigger", "triggerGroup")  
			    .withSchedule(CronScheduleBuilder.cronSchedule("0 42 10 * * ? *"))  
			    .startNow().build();

		scheduler.scheduleJob(jobDetail, trigger1);
		scheduler.start();

	}
}
