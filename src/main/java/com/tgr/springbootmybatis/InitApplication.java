package com.tgr.springbootmybatis;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApplication implements ApplicationRunner {

	private static Logger logger = LoggerFactory.getLogger(InitApplication.class);

	@Value("${file.store.path}")
	private String fileStorePath;

	private String[] dirs = { "avatar", "customer", "fault_pic", "goods", "result_pic", "upload" };

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Timer timer = new Timer("init config");
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				initDirs();
				logger.warn("OK, completed");
			}
		}, 1000);
	}

	private void initDirs() {
		for (String dir : dirs) {
			File d = new File(fileStorePath, dir);
			if (!d.exists()) {
				d.mkdirs();
			}
		}
	}

}
