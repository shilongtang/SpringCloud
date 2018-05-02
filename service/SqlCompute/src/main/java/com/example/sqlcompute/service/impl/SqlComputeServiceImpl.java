package com.example.sqlcompute.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blinkfox.zealot.bean.SqlInfo;
import com.blinkfox.zealot.config.ZealotConfigManager;
import com.blinkfox.zealot.core.Zealot;
import com.example.sqlcompute.service.SqlComputeService;
import com.example.sqlcompute.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SqlComputeServiceImpl implements SqlComputeService {
    protected Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PageSql pageSql;

    /**
     *  @PostConstruct 注解
     * 服务器加载Servlet的时候运行
     * 服务启动时运行
     */
    @PostConstruct
    private void init() {
        //使用扫描包的方式 zealot 1.3.0 新增方式
        ZealotConfigManager.getInstance().initLoadXmlLocations("sql/xml");
    }

    @Override
    public String querySqlByPage(String json) {
        ParamPage param = JSON.parseObject(json, ParamPage.class);

        Pageable pageable = param.getPageable();

        SqlInfo sqlInfo = Zealot.getSqlInfo(param.getSqlFile(), param.getSqlKey(), param.getParamMap());
        String sql = sqlInfo.getSql();
        Object[] params = sqlInfo.getParamsArr();

        String countSqlstr = pageSql.buildCount(sql);
        logger.info("执行查询数量sql：{}", countSqlstr);
        logger.info("执行参数：{}", params);
        Integer count = jdbcTemplate.queryForObject(countSqlstr, params, Integer.class);
        pageable.setTotalCount(count);

        params = PageParam.build(params, pageable);

        List<Map<String, Object>> list = new ArrayList<>();

        if (pageable.check()) {
            String pageSqlstr = pageSql.buildPage(sql);
            logger.info("执行分页查询sql：{}", pageSqlstr);
            logger.info("执行参数：{}", params);
            list = jdbcTemplate.queryForList(pageSqlstr, params);
        }

        ResultPageable pageData = new ResultPageable(
                param.getPageable().getPageNumber(),
                param.getPageable().getPageSize(),
                pageable.getTotalCount(),
                pageable.getTotalPage(),
                JSON.toJSONString(list));
        return pageData.toJson();
    }

    @Override
    public String querySql(String json) {
        Param param = JSON.parseObject(json, Param.class);
        SqlInfo sqlInfo = Zealot.getSqlInfo(param.getSqlFile(), param.getSqlKey(), param.getParamMap());
        String sql = sqlInfo.getSql();
        Object[] params = sqlInfo.getParamsArr();
        logger.info("执行sql：{}", sql);
        logger.info("执行参数：{}", params);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, params);
        return JSON.toJSONString(mapList);
    }


    @Override
    public String exportExcel(String json) {
       /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String data = request.getParameter("data");
        MultiSheetExcelExportVo multiSheetExcelExportVo = JSONObject
                .parseObject(data, MultiSheetExcelExportVo.class);
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getResponse();

            response.setContentType("application/binary;charset=ISO8859-1");
            String fileName = new String(multiSheetExcelExportVo.getExportFileName().getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 组装附件名称和格式
            ServletOutputStream outputStream = response.getOutputStream();
            PoiExportExcel.exportMultiSheetExcel(multiSheetExcelExportVo, outputStream);
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }*/
        return null;
    }

    public static void main(String[] args) {
        String json = "{\"data\":[[\"0004.01.001\",\"购物中心/11#\",null,\"预测阶段\",1,\"df\"],"
                + "[\"0004.01\",\"购物中心/一期\",\"上海公司\",\"预测阶段\",3,\"df\"],"
                + "[\"0003.0002\",\"西部世界/查出\",null,null,1,\"df\"],"
                + "[null,\"undefined/undefined\",null,null,1,\"df\"],"
                + "[null,\"undefined/undefined\",null,null,1,null],"
                + "[null,\"undefined/undefined\",null,null,2,\"sdn\"],"
                + "[null,\"undefined/undefined\",\"上海公司\",\"预测阶段\",4,\"sdn\"],"
                + "[null,\"undefined/undefined\",null,null,1,\"df\"],"
                + "[null,\"undefined/undefined\",null,\"惺惺惜惺惺\",1,\"df\"],"
                + "[null,\"undefined/undefined\",null,\"预测阶段\",1,\"df\"]]}";

        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray data = jsonObject.getJSONArray("data");
       /* List<String[]> areas = data.toJavaList(String[].class);
        for (String[] area : areas) {
            for (int i = 0; i < area.length; i++) {
                String s = area[i];
            }
        }*/
    }
}
