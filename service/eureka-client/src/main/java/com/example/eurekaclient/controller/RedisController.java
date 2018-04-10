package com.example.eurekaclient.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.eurekaclient.model.ApiResponse;
import com.example.eurekaclient.model.User;
import com.example.eurekaclient.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * APP账号管理服务
 */
@Api(description="Spring Boot redis", tags = "REDIS API")
@ConditionalOnWebApplication
@RestController
@RequestMapping("/redis")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedisController extends BaseController {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 将一个用户存入到redis中
     * @param user
     * @return
     */
    @ApiOperation(value="将一个用户存入redis中")
    @PutMapping(value = "/service/addUserToRedis")
    public ApiResponse insert(@RequestBody @ApiParam(name="用户对象",value="传入json格式",required=true) User user)  {
        stringRedisTemplate.opsForValue().set(user.getId(),JSONObject.toJSONString(user),600, TimeUnit.SECONDS);
        return super.callback(true);
    }

    /**
     * 向redis中存入一个数据
     * @param key
     * @param value
     * @param time
     * @param type
     * @return
     */
    @ApiOperation(value="向redis中存入一个数据")
    @PostMapping(value = "/service/addRedisValue")
    public ApiResponse addKey(@RequestParam String key,@RequestParam String value,@RequestParam Integer time,@RequestParam String type)  {
        TimeUnit seconds = TimeUnit.SECONDS;
        if(StringUtils.equals("m",type)){
            seconds = TimeUnit.SECONDS;
        }
        stringRedisTemplate.opsForValue().set(key,value,time, seconds);
        return super.callback(true);
    }

    /**
     * val操作
     * @param key
     * @param value
     * @return
     */
    @ApiOperation(value="val操作")
    @PostMapping(value = "/service/redisValueChange")
    public ApiResponse redisKeyValueMinus(@RequestParam String key,@RequestParam Integer value)  {
        stringRedisTemplate.boundValueOps(key).increment(value);
        return super.callback(true);
    }

    /**
     * 获取一个key的过期时间
     * @param key
     * @return
     */
    @ApiOperation(value="获取一个key的过期时间")
    @PostMapping(value = "/service/getRedisByKeyTime")
    public ApiResponse getKyeTime(@RequestParam String key)  {
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return super.callbackSuccess(expire.toString());
    }

    /**
     * 根据id从redis中查询用户数据
     * @param id
     * @return
     */
    @ApiOperation(value="根据id从redis中查询用户数据")
    @GetMapping(value = "/service/findRedisUserByid/{id}")
    public ApiResponse getRedisUserById(@PathVariable String id)  {
        String s = stringRedisTemplate.opsForValue().get(id);
        User user = JSONObject.parseObject(s, User.class);
        return super.callbackSuccess(JSONObject.toJSONString(user));
    }
    /**
     * 根据id从redis中查询数据
     * @param id
     * @return
     */
    @ApiOperation(value="根据id从redis中查询数据")
    @GetMapping(value = "/service/findRedisByid/{id}")
    public ApiResponse getRedisById(@PathVariable String id)  {
        String s = stringRedisTemplate.opsForValue().get(id);
        return super.callbackSuccess(s);
    }
}
