package com.example.useraccount.controller;


import com.example.useraccount.model.UserAccount;
import com.example.useraccount.utils.ApiResponse;
import com.example.useraccount.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "账号管理服务",tags = "UserAccount")
@RestController
@ConditionalOnWebApplication
@RequestMapping("api")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserAccountController extends BaseController {

    /**
     * 新增
     */
    @ApiOperation(value="新增账号 传了id则修改")
    @PutMapping(value = "/userAccount/service/save")
    public ApiResponse save(@RequestBody @ApiParam(name="用户对象",value="传入json格式",required=true) UserAccount userAccount)  {

        return super.callback(true);
    }

}
