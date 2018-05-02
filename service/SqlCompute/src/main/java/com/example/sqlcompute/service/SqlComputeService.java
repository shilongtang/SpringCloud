package com.example.sqlcompute.service;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public interface SqlComputeService {
    /**
     * default 可以使接口有默认的方法体
     * @return
     */
    default String unimplement(){
        Map<String, String> map = new HashMap<>();
        map.put("message", "该方法实现");
        return JSON.toJSONString(map);
    }

    default String querySqlByPage(String body){
        return unimplement();
    };
    default String querySql(String body){
        return unimplement();
    };
    default String exportExcel(String body){
        return unimplement();
    };
}
