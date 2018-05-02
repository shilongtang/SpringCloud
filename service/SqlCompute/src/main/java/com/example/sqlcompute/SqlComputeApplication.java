package com.example.sqlcompute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SqlComputeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqlComputeApplication.class, args);
	}
}
