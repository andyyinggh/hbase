package cn.edu.cuit.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 
 * @author guanghua.ying
 * @date 2017年7月10日 下午5:40:43
 */
public class Test {

	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobDetail job = newJob(TestJob.class)
				.withIdentity("job1", "group1")
				.build();
		
		Trigger trigger = newTrigger()
				.withIdentity("trigger1", "group1")
				.startNow()
				.withSchedule(simpleSchedule()
						.withIntervalInSeconds(5)//每5秒触发一次
						.repeatForever())
				.build();

		scheduler.scheduleJob(job, trigger);
		scheduler.start();

	}
}
