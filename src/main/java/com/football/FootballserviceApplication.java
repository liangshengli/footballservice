package com.football;

import com.football.common.ConfigBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ConfigBean.class})
public class FootballserviceApplication  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FootballserviceApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(FootballserviceApplication.class, args);
	}

	@Bean
	public TaskScheduler taskScheduler(){
		ThreadPoolTaskScheduler taskScheduler=new ThreadPoolTaskScheduler();
		//线程池大小
		taskScheduler.setPoolSize(10);
		//线程名字前缀
		taskScheduler.setThreadNamePrefix("springboot-task");
		return taskScheduler;
	}
}
