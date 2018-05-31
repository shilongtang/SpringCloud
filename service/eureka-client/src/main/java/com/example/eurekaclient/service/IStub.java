package com.example.eurekaclient.service;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eureka-client
 * @description:
 * @author: 食探.shilongtnag
 * @create: 2018-05-30 17:23
 **/
public interface IStub {
    @FeignClient(name = "sql-compute",fallbackFactory=SqlServiceHystrixClientFallbackFactory.class)
    interface SqlService {
        @GetMapping("/service/querySql")
        String querySql(@RequestBody String body, @RequestHeader("Authorization") String authorization, @RequestHeader("InvocationFlag") String invocationFlag);  }

    @Component
    public static class SqlServiceHystrixClientFallbackFactory implements FallbackFactory<SqlService> {
        @Override
        public SqlService create(Throwable throwable) {
            return null;
        }
    }


}
