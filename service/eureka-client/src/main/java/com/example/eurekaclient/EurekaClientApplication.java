package com.example.eurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//激活Eureka中的DiscoveryClient实现，这样才能实现Controller中对服务信息的输出。
@EnableTransactionManagement  // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@EnableDiscoveryClient
@EnableScheduling // 注解，启用定时任务的配置
@SpringBootApplication
public class EurekaClientApplication {


	@Bean
	public Object testBean(PlatformTransactionManager platformTransactionManager) {
		System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
		return new Object();
	}
	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}
}
