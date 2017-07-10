package cn.edu.cuit.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 
 * @author guanghua.ying
 * @date 2017年7月10日 下午4:55:15
 */
public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		JobDetail jobDetail = context.getJobDetail();
//		jobDetail.getJobDataMap();
		System.out.println(new Date() + ":开始执行Job");
		
	}

}
