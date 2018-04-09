package com.example.eurekaclient.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * APP账号管理服务
 */
@Api(description="User服务", tags = "API")
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



}
