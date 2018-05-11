package com.example.useraccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@EnableScheduling // 注解，启用定时任务的配置
@SpringBootApplication
public class UseraccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(UseraccountApplication.class, args);
	}
}
