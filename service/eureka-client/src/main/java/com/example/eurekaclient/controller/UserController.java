package com.example.eurekaclient.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.util.List;


@Api(description="User服务", tags = "USER API")
@ConditionalOnWebApplication
@RestController
@RequestMapping("/api")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserController extends BaseController {
    @Autowired
    UserService userService;
    /**
     * 新增
     */
    @ApiOperation(value="新增User---使用 JdbcTemplate")
    @PutMapping(value = "/service/addUser")
    public ApiResponse insert(@RequestBody  @ApiParam(name="用户对象",value="传入json格式",required=true) User user)  {
        userService.addUser(user.getId(),user.getName(),user.getAge());
        return super.callback(true);
    }

    /**
     * 新增
     */
    @ApiOperation(value="新增User---使用 JPA")
    @PutMapping(value = "/service/save")
    public ApiResponse save(@RequestBody  @ApiParam(name="用户对象",value="传入json格式",required=true) User user)  {
        userService.save(user);
        user.setName(null);
        userService.save(user);
        return super.callback(true);
    }

    /**
     * 查询
     */
    @ApiOperation(value="根据name查询User---使用 JPA")
    @PostMapping(value = "/service/findByName")
    public ApiResponse findByName(@RequestParam(required = false) String name)  {
        List<User> byNname = userService.findByNname(name);
        return super.callbackSuccess(JSONArray.toJSONString(byNname));
    }

    /**
     * 删除
     */
    @ApiOperation(value="根据id删除User---使用 JPA")
    @DeleteMapping(value = "/service/{id}")
    public ApiResponse deleteById(@PathVariable String id)  {
        userService.deleteUserByid(id);
        return super.callback(true);
    }
    /**
     * 根据id查询用户
     */
    @ApiOperation(value="根据id查询用户---使用 JPA")
    @GetMapping(value = "/service/{id}")
    public ApiResponse getUserById(@PathVariable String id)  {
        User one = userService.findOne(id);
        return super.callbackSuccess(JSONObject.toJSONString(one));
    }

}
