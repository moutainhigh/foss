package com.deppon.pda.bdm.module.foss.monitor.server.job;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 调度服务类
 * @author mt
 * 2012-11-18
 */
public class SchedulerService {
	private static SchedulerService instance = null;
	
	public static SchedulerService getInstantce(){
		if(instance == null){
			instance = new SchedulerService();
		}
		return instance;
	}
	
	private SchedulerService() {
	}
	
	public void task() throws SchedulerException {
		// 初始化 ScheduleFactory
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		//通过工厂方法获取Scheduler
		Scheduler scheduler = schedulerFactory.getScheduler();
		
		//创建监控Job
		JobDetail monitorJobDetail = new JobDetail("monitorJobDetail", "monitorJobDetailGroup",
				MonitorDataClearJob.class);
		
		
		//创建监控触发器
		CronTrigger monitorCronTrigger = new CronTrigger("monitorCronTrigger",
				"monitorCronTriggerGroup");
		
		try {
			/*
			1．秒（0–59）
			2．分钟（0–59）
			3．小时（0–23）
			4．月份中的日期（1–31）
			5．月份（1–12或JAN–DEC）
			6．星期中的日期（1–7或SUN–SAT）
			7．年份（1970–2099）
			*/
			//监控表达式1点调用一次0 0 1 * * ?
			//CronExpression monitorCexp = new CronExpression("0 0 12 * * ?");
			CronExpression monitorCexp = new CronExpression("0 0 1 * * ?");
			
			//设置Cron表达式
			monitorCronTrigger.setCronExpression(monitorCexp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置job,以及触发器
		scheduler.scheduleJob(monitorJobDetail, monitorCronTrigger);
		//开始进行调度
		scheduler.start();
	}
	
	public static void main(String[] args) throws Exception {
		SchedulerService.getInstantce().task();
	}
}
