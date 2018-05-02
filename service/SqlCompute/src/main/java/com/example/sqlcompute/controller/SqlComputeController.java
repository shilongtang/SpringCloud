package com.example.sqlcompute.controller;

import com.example.sqlcompute.service.SqlComputeService;
import com.example.sqlcompute.utils.ApiResponse;
import com.example.sqlcompute.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description="sql  服务", tags = "SQL API")
@ConditionalOnWebApplication
@RestController
@RequestMapping("/api")
public class SqlComputeController extends BaseController {

    @Autowired
    private SqlComputeService sqlComputeService;

    /**
     * 查询sql带分页
     */
    @PostMapping(value = "/service"+"/querySqlByPage")
    @ApiOperation(value="定制接口: 查询sql带分页")
    public ApiResponse querySqlByPage(@RequestBody String body) {
        try {
            String s = sqlComputeService.querySqlByPage(body);
            return super.callbackSuccess(sqlComputeService.querySqlByPage(body));
        }catch (Exception e){
            String _message = "查询sql带分页失败: "+e.getMessage();
            logger.error(_message, e);
            Map<String, Object> map = new HashMap<>();
            map.put("message", _message);
            return super.callbackFail(map);
        }
    }
    /**
     * 查询sql不带分页
     */
    @PostMapping(value = "/service"+"/querySql")
    @ApiOperation(value="定制接口: 查询sql不带分页")
    public ApiResponse querySql(@RequestBody String body) {
        try {
            return super.callbackSuccess(sqlComputeService.querySql(body));
        }catch (Exception e){
            String _message = "查询sql不带分页失败: "+e.getMessage();
            logger.error(_message, e);
            Map<String, Object> map = new HashMap<>();
            map.put("message", _message);
            return super.callbackFail(map);
        }
    }
    /**
     * 分页查询SDDict
     *//*
    @PostMapping(value = "/service"+"/querySDDictPageList")
    @ApiOperation(value="定制接口: 分页查询SDDict")
    public ApiResponse querySDDictPageList(@RequestBody String body) {
        try {
            return super.callbackSuccess(sqlComputeSvcService.querySDDictPageList(body));
        }catch (Exception e){
            String _message = "分页查询SDDict失败: "+e.getMessage();
            logger.error(_message, e);
            printStackTrace(e);
            Map<String, Object> map = new HashMap<>();
            map.put("message", _message);
            return super.callbackFail(map);
        }
    }*/
    /**
     * 导出excel文档
     */
    @PostMapping(value = "/service"+"/exportExcel")
    @ApiOperation(value="定制接口: 导出excel文档")
    public ApiResponse exportExcel(@RequestBody String body) {
        try {
            return super.callbackSuccess(sqlComputeService.exportExcel(body));
        }catch (Exception e){
            String _message = "导出excel文档失败: "+e.getMessage();
            logger.error(_message, e);
            printStackTrace(e);
            Map<String, Object> map = new HashMap<>();
            map.put("message", _message);
            return super.callbackFail(map);
        }
    }
}
