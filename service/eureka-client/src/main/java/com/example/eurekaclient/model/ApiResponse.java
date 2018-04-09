package com.example.eurekaclient.model;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

    private ApiResponseState state;
    private Object body;

    public ApiResponse(ApiResponseState state, Map<String, Object> body) {
        this.state = state;
        if(body == null) {
            this.body = new HashMap();
        }else{
            this.body = body;
        }
    }

    public ApiResponse(ApiResponseState state, String body) {
        this.state = state;
        if(StringUtils.isBlank(body)) {
            this.body = new HashMap();
        }else{
            try{
                this.body = JSON.parseObject(body, Object.class);
            }catch (Exception e){
                Map<String, String> map = new HashMap<>();
                map.put("message", body);
                this.body = map;
            }
        }
    }

    public ApiResponseState getState() {
        return state;
    }

    public void setState(ApiResponseState state) {
        this.state = state;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
