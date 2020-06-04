package com.tgr.springbootmybatis.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DumbJob implements Job{

	public DumbJob() {
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.err.println("呼哈哈 我看五天就够了");
	}
}
