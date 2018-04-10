package com.example.eurekaclient.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.*;

public interface BaseService<T> {

    Logger LOGGER = LoggerFactory.getLogger(BaseService.class);
    default T findUniqueByProperty(String propertyName, Object propertyValue){
        if(StringUtils.isBlank(propertyName) ||
                propertyValue == null ||
                StringUtils.isBlank(propertyValue.toString())){
            return null;
        }
        List<T> list = findByProperty(propertyName, propertyValue);
        if(list == null || list.isEmpty()){
            return null;
        }
        if(list.size() > 1){
            LOGGER.info("根据属性 {} 及值 {} 查找唯一记录失败, 查找到 {} 条记录, class: {}", propertyName, propertyValue, list.size(), this.getClass().getSimpleName());
            return null;
        }
        return list.get(0);
    }

    default String toMultiProperty(List<Object> propertyValue){
        StringBuilder str = new StringBuilder();
        for(Object v : propertyValue){
            if(v != null && !StringUtils.isBlank(v.toString())){
                str.append(v.toString()).append(",");
            }
        }
        if(str.length()==0){
            return null;
        }
        str.setLength(str.length()-1);
        return str.toString();
    }

    default List<T> findByProperty(String propertyName, List<Object> propertyValue){
        return findByProperty(propertyName, toMultiProperty(propertyValue));
    }

    default List<T> findByProperty(String propertyName, Object propertyValue){
        if(propertyValue instanceof List){
            propertyValue = toMultiProperty((List)propertyValue);
        }
        return findByProperty(propertyName, propertyValue, 1, Integer.MAX_VALUE);
    }

    default List<T> findByProperty(String propertyName, Object propertyValue, int pageNumber, int pageSize){
        return findByProperty(propertyName, propertyValue, pageNumber, pageSize, Sort.Direction.DESC, "updateDate");
    }

    default List<T> findByProperty(String propertyName, Object propertyValue, Sort.Direction direction, String... properties){
        return findByProperty(propertyName, propertyValue, 1, Integer.MAX_VALUE, direction, properties);
    }

    default List<T> findByProperty(String propertyName, Object propertyValue, int pageNumber, int pageSize, Sort.Direction direction, String... properties){
        return findByProperty(Relation.and, pageNumber, pageSize, Arrays.asList(propertyName), Arrays.asList(propertyValue==null?"":propertyValue.toString()), direction, properties);
    }

    default List<T> findByProperty(List<String> propertyName, List<String> propertyValue, Relation relation){
        return findByProperty(relation, 1, Integer.MAX_VALUE, propertyName, propertyValue, Sort.Direction.DESC, "updateDate");
    }

    default  List<T> findByProperty(Relation relation, List<String> propertyName, List<String> propertyValue, Sort.Direction direction, String... properties){
        return findByProperty(relation, 1, Integer.MAX_VALUE, propertyName, propertyValue, direction, properties);
    }

    default List<T> findByProperty(Relation relation, List<String> propertyName, List<String> propertyValue){
        return findByProperty(relation, 1, Integer.MAX_VALUE, propertyName, propertyValue, Sort.Direction.DESC, "updateDate");
    }

    default  List<T> findByProperty(Relation relation, int pageNumber, int pageSize, List<String> propertyName, List<String> propertyValue, Sort.Direction direction, String... properties){
        Page<T> page = findByProperty(relation, propertyName, propertyValue, pageNumber, pageSize, direction, properties);
        if(page != null && page.getContent() != null){
            return page.getContent();
        }
        return Collections.emptyList();
    }

    default Page<T> findByProperty(Relation relation, List<String> propertyName, List<String> propertyValue, int pageNumber, int pageSize){
        return findByProperty(relation, propertyName, propertyValue, pageNumber, pageSize, Sort.Direction.DESC, "updateDate");
    }

    default Page<T> findByProperty(Relation relation, List<String> propertyName, List<String> propertyValue, int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        for(String name : propertyName){
            if(name==null){
                String info = "非法查询, 属性名称包含null, propertyName: "+propertyName+", propertyValue: "+propertyValue+", pageNumber: "+pageNumber+", pageSize: "+pageSize+", direction: "+direction+", properties: "+Arrays.asList(properties);
                LOGGER.error(info);
                throw new RuntimeException(info);
            }
        }
        for(String value : propertyValue){
            if(value==null){
                String info = "非法查询, 属性值包含null, propertyName: "+propertyName+", propertyValue: "+propertyValue+", pageNumber: "+pageNumber+", pageSize: "+pageSize+", direction: "+direction+", properties: "+Arrays.asList(properties);
                LOGGER.error(info);
                throw new RuntimeException(info);
            }
        }
        if(LOGGER.isInfoEnabled()) {
            Set<String> fields = new HashSet<>();
            fields.addAll(propertyName);
            for (String property : properties) {
                fields.add(property);
            }
            String doName = getDoName();
            for (String field : fields) {
                LOGGER.info("findByProperty monitor {} {}", doName, field);
            }
            if(propertyName.size() > 1){
                LOGGER.info("multicolumn search monitor {} {}", doName, propertyName);
            }
        }
        Page<T> page = findByPropertyImpl(relation, propertyName, propertyValue, pageNumber, pageSize, direction, properties);
        if(LOGGER.isDebugEnabled()){
            String debug = "查询条件: propertyName: "+propertyName+", propertyValue: "+propertyValue+", pageNumber: "+pageNumber+", pageSize: "+pageSize+", direction: "+direction+", properties: "+Arrays.asList(properties)+", "
                    +"查询结果: "+page.getContent();
            LOGGER.debug(debug);
        }
        return page;
    }

    default String getDoName(){
        return this.getClass().getSimpleName().replace("ServiceImpl", "");
    }

    Page<T> findByPropertyImpl(Relation relation, List<String> propertyName, List<String> propertyValue, int pageNumber, int pageSize, Sort.Direction direction, String... properties);

}
