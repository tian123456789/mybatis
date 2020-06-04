package com.tgr.springbootmybatis.quartz;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerUtils;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.triggers.SimpleTriggerImpl;

public class Test1 {

	public static void main1(String[] args) throws SchedulerException {
		
		SchedulerFactory scheFact = new StdSchedulerFactory();
		Scheduler scheduler = scheFact.getScheduler();
		scheduler.start();
		
		
	}
	
	/**
	 * start and end
	 * @throws SchedulerException 
	 */
	@Test
	public void test1() throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		scheduler.shutdown();
	}
	
	
	@Test
	public void test2() throws SchedulerException {
		
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		
		JobDetail jobDetail = new JobDetailImpl("myJob", null , DumbJob.class);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, 10);
		Date date = calendar.getTime();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("myTrigger")
				.withSchedule(
						SimpleScheduleBuilder
								.simpleSchedule()
								.withIntervalInMilliseconds(10)
								.repeatForever()
				)
				.startAt(date)
				.build();
		
		scheduler.scheduleJob(jobDetail , trigger);
		
	}
	
	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		
		JobDetail jobDetail = new JobDetailImpl("myJob", null , DumbJob.class);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, 10);
		Date date = calendar.getTime();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("myTrigger")
				.withSchedule(
						SimpleScheduleBuilder
								.simpleSchedule()
								.withIntervalInMilliseconds(10)
								.repeatSecondlyForTotalCount(10)
								
				)
				.startAt(date)
				.build();
		
		scheduler.scheduleJob(jobDetail , trigger);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

