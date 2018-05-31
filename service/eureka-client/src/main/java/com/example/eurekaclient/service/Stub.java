package com.example.eurekaclient.service;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface Stub {
    @Component
    class SqlService{
        @Autowired(required = false)
        protected IStub.SqlService sqlService;
        public String querySql(@RequestBody String body){
            return sqlService.querySql(body,MDC.get("Authorization"),"true");
        }
    }
}
