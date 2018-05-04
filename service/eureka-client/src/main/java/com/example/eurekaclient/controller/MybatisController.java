package com.example.eurekaclient.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.eurekaclient.mapper.UserMapper;
import com.example.eurekaclient.model.ApiResponse;
import com.example.eurekaclient.model.User;
import com.example.eurekaclient.service.UserService;
import com.example.eurekaclient.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(description="Spring Boot Mybatis", tags = "MYBATIS API")
@ConditionalOnWebApplication
@RestController
@RequestMapping("/api")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MybatisController extends BaseController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;
    /**
     * 新增User   属性传参
     * @param id
     * @param name
     * @param age
     * @return
     */
    @ApiOperation(value="新增User 属性传参")
    @PutMapping(value = "/mybatis/service/addUser")
    public ApiResponse insert(@RequestParam String id,@RequestParam String name,@RequestParam Integer age)  {
        userService.maddUser(id, name, age);
        userService.maddUser("12312313", null, 12);
        return super.callback(true);
    }
    /**
     * 新增User 对象传参
     * @param user
     * @return
     */
    @ApiOperation(value="新增User 对象传参")
    @PutMapping(value = "/mybatis/service/addObjectParamUser")
    public ApiResponse insertObjectParam(@RequestBody @ApiParam(name="用户对象",value="传入json格式",required=true) User user)  {
        userMapper.insertObject(user);
        return super.callback(true);
    }

    /**
     * 新增User Map传参
     * @param body
     * @return
     */
    @ApiOperation(value="新增User Map传参")
    @PostMapping(value = "/mybatis/service/addMapUser")
    public ApiResponse insertMapParam(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);//先转换成Object
        Map map = (Map)jsonObject;         //Object强转换为Map
        userMapper.insertMap(map);
        userMapper.delete("sadsadsadsad");

        return super.callback(true);
    }
    /**
     * 根据用户名查询
     * @param name
     * @return
     */
    @ApiOperation(value="根据用户名查询 返回map方式")
    @PostMapping(value = "/mybatis/service/findByname")
    public ApiResponse findByname(@RequestParam String name)  {
        Map byname = userMapper.findByname(name);
        return super.callbackSuccess(byname);
    }

    /**
     * 根据用户id删除用户
     * @param id
     * @return
     */
    @ApiOperation(value="根据用户id删除用户")
    @DeleteMapping(value = "/mybatis/service/delete{id}")
    public ApiResponse delete(@RequestParam String id)  {
        userMapper.delete(id);
        return super.callback(true);
    }
    /**
     * 根据id修改用户
     * @param user
     * @return
     */
    @ApiOperation(value="根据用户id删除用户")
    @PutMapping(value = "/mybatis/service/update")
    public ApiResponse update(@RequestBody @ApiParam User user)  {
        userMapper.update(user);
        return super.callback(true);
    }

}
