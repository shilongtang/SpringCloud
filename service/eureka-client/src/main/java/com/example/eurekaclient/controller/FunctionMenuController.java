package com.example.eurekaclient.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.eurekaclient.model.ApiResponse;
import com.example.eurekaclient.model.FunctionMenu;
import com.example.eurekaclient.service.FunctionMenuService;
import com.example.eurekaclient.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description="菜单服务", tags = "FunctionMenu API")
@ConditionalOnWebApplication
@RestController
@RequestMapping("/menu")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FunctionMenuController extends BaseController {

    @Autowired
    FunctionMenuService functionMenuService;
    /**
     * 新增
     */
    @ApiOperation(value="新增User")
    @PutMapping(value = "/service/save")
    public ApiResponse save(@RequestBody @ApiParam(name="用户对象",value="传入json格式",required=true) FunctionMenu functionMenu)  {

        FunctionMenu save = functionMenuService.save(functionMenu);

        return super.callbackSuccess(JSONObject.toJSONString(save));
    }
    /**
     * 删除
     */
    @ApiOperation(value="删除根据User")
    @DeleteMapping(value = "/service/delete/{id}")
    public ApiResponse delete(@PathVariable String id)  {

      functionMenuService.deleteUserByid(id);

      return super.callbackSuccess("{}");
    }

    /**
     * List
     */
    @ApiOperation(value="List")
    @GetMapping(value = "/service/list/{number}/{size}")
    public ApiResponse delete(@PathVariable Integer number,@PathVariable Integer size)  {
        Page<FunctionMenu> list = functionMenuService.list(number, size);
        JSONObject result = new JSONObject();
        result.put("pageNumber",number);
        result.put("pageSize",size);
        result.put("totalCount",list.getTotalElements());//总数量
        result.put("list",list.getContent());
        return super.callbackSuccess(JSONObject.toJSONString(result));
    }
}
