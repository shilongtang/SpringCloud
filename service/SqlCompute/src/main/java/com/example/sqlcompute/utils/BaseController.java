package com.example.sqlcompute.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


public class BaseController {
    protected boolean returnExceptionStackTraceToFront =true;

    protected Logger logger= LoggerFactory.getLogger(getClass());

    protected ApiResponse callback(boolean success) {
        Map<String, Object> map = new HashMap<>();
        if(success){
            map.put("success", true);
            return callbackSuccess(JSON.toJSONString(map));
        }else{
            map.put("success", false);
            return callbackFail(map);
        }
    }

    protected ApiResponse callbackSuccess(Map<String, Object> map) {
        String json = "{}";
        if(map != null){
            json = JSON.toJSONString(map);

        }
        return callbackSuccess(json);
    }


    protected ApiResponse callbackSuccess(String json) {
        if(StringUtils.isNotBlank(json)) {
            try{
                JSON.parseObject(json, Object.class);
            }catch (Exception e){
                Map<String, Object> map = new HashMap<>();
                map.put("message", json);
                return callbackSuccess(map);
            }
        }
        return new ApiResponse(new ApiResponseState(ApiResponseCode.SUCCESS_CODE, ApiResponseCode.SUCCESS_MESSAGE), json);
    }

    protected ApiResponse callbackFail(Map<String, Object> data) {
        return new ApiResponse(new ApiResponseState(ApiResponseCode.ERROR_CODE, ApiResponseCode.ERROR_MESSAGE), JSON.toJSONString(data));
    }

    protected ApiResponse callbackFail(String json ) {
        if(StringUtils.isNotBlank(json)) {
            try{
                JSON.parseObject(json, Object.class);
            }catch (Exception e){
                Map<String, Object> map = new HashMap<>();
                map.put("message", json);
                return callbackFail(map);
            }
        }
        return new ApiResponse(new ApiResponseState(ApiResponseCode.ERROR_CODE, ApiResponseCode.ERROR_MESSAGE), json);
    }

    protected ApiResponse callbackFail(String message, Map<String, Object> data) {
        return callbackFail(message, JSON.toJSONString(data));
    }

    protected ApiResponse callbackFail(String message, String json) {
        if(StringUtils.isNotBlank(json)) {
            try{
                JSON.parseObject(json, Object.class);
            }catch (Exception e){
                Map<String, Object> map = new HashMap<>();
                map.put("message", json);
                return callbackFail(message, map);
            }
        }
        return new ApiResponse(new ApiResponseState(ApiResponseCode.ERROR_CODE, message), json);
    }
}
