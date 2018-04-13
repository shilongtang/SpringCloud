package com.example.eurekaclient.service;

import com.example.eurekaclient.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.*;
import java.util.*;

public interface BaseService<T> {

    default PageRequest getPageRequest(int pageNumber, int pageSize, int pageMaxSize){
        return getPageRequest(pageNumber, pageSize, pageMaxSize, Sort.Direction.DESC, "id");
    }

    default PageRequest getPageRequest(int pageNumber, int pageSize, int pageMaxSize, Sort.Direction direction, String... properties){
        return getPageRequest(pageNumber, pageSize, pageMaxSize, (direction == null || properties == null || properties.length == 0) ? null : new Sort(direction, properties));
    }

    default PageRequest getPageRequest(int pageNumber, int pageSize, int pageMaxSize, Sort sort){
        if(pageNumber < 1){
            pageNumber = 1;
        }
        if(pageSize < 1){
            pageSize = 10;
        }
        if(pageSize > pageMaxSize){
            pageSize = pageMaxSize;
        }
        return new PageRequest(pageNumber-1, pageSize, sort);
    }
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
    default Predicate getPredicate(String property, String value, Root<?> root, CriteriaBuilder cb){
        if(StringUtils.isBlank(property)){
            LOGGER.error("属性为空");
            return null;
        }
        if(StringUtils.isBlank(value)){
            LOGGER.error("值为空, property: {}", property);
            return null;
        }
        property=property.trim();
        value=value.trim();
        if("GE".equals(property)
                || "GT".equals(property)
                || "LE".equals(property)
                || "LT".equals(property)
                || "EQ".equals(property)
                || "NE".equals(property)){
            String[] attr = value.replace(",", ":").replace(";", ":").split(":");
            if(attr == null || attr.length != 2){
                return null;
            }
            Path p1 = root.get(attr[0]);
            Path p2 = root.get(attr[1]);
            if("GE".equals(property)) {
                return cb.ge(p1, p2);
            }else if("GT".equals(property)) {
                return cb.greaterThan(p1, p2);
            }else if("LE".equals(property)) {
                return cb.le(p1, p2);
            }else if("LT".equals(property)) {
                return cb.lessThan(p1, p2);
            }else if("EQ".equals(property)) {
                return cb.equal(p1, p2);
            }else if("NE".equals(property)) {
                return cb.notEqual(p1, p2);
            }
        }
        if(value.equalsIgnoreCase("is null")){
            return cb.isNull(root.get(property));
        }
        if(value.equalsIgnoreCase("is not null")){
            return cb.isNotNull(root.get(property));
        }
        if(value.equalsIgnoreCase("is empty")){
            return cb.equal(root.get(property), "");
        }
        if(value.equalsIgnoreCase("is not empty")){
            return cb.notEqual(root.get(property), "");
        }
        if(value.contains("[") && value.contains("_") && value.contains("]")){
            value = value.replace("[", "").replace("]", "");
            String[] attr = value.split("_");
            if(attr == null || attr.length != 2){
                LOGGER.error("表达式不正确, property: {}, value: {}", property, value);
                return null;
            }
            if(StringUtils.isNumeric(attr[0].replace(".", ""))
                    && StringUtils.isNumeric(attr[1].replace(".", ""))){
                return cb.between(root.get(property), Float.parseFloat(attr[0]), Float.parseFloat(attr[1]));
            }
            Long start = TimeUtils.fromString(attr[0]);
            Long end = TimeUtils.fromString(attr[1]);
            if(start != null && end != null){
                return cb.between(root.get(property), new Date(start), new Date(end));
            }else {
                return cb.between(root.get(property), attr[0], attr[1]);
            }
        }
        if(value.startsWith("eq:")){
            return cb.equal(root.get(property), getValue(value.substring(3)));
        }else if(value.startsWith("ne:")){
            return cb.notEqual(root.get(property), getValue(value.substring(3)));
        }else if(value.startsWith("lt:")){
            return cb.lessThan(root.get(property), getValue(value.substring(3)).toString());
        }else if(value.startsWith("le:")){
            return cb.lessThanOrEqualTo(root.get(property), getValue(value.substring(3)).toString());
        }else if(value.startsWith("gt:")){
            return cb.greaterThan(root.get(property), getValue(value.substring(3)).toString());
        }else if(value.startsWith("ge:")){
            return cb.greaterThanOrEqualTo(root.get(property), getValue(value.substring(3)).toString());
        }else if(value.startsWith("like:")){
            value = value.substring(5);
            value = "%" + value + "%";
            return cb.like(root.get(property), value);
        }else {
            return cb.equal(root.get(property), getValue(value));
        }
    }
    Page<T> list(int pageNumber, int pageSize);

    Page<T> list(int pageNumber, int pageSize, Sort.Direction direction, String... properties);

    default Object getValue(String value){
        if(value.startsWith("string:")){
            return value.substring(7);
        }
        if(StringUtils.isNumeric(value)){
            return Integer.parseInt(value);
        }
        if(StringUtils.isNumeric(value.replace(".", ""))){
            return Float.parseFloat(value);
        }
        Long date = TimeUtils.fromString(value);
        if(date != null && date > 0){
            return new Date(date);
        }
        return value;
    }
    default Predicate getCompoundPredicate(String property, String value, Root<?> root, CriteriaBuilder cb){
        if(value.contains(",")){
            List<Predicate> orPredicates = new ArrayList<>();
            for(String _v : value.split(",")){
                Predicate p = getPredicate(property, _v, root, cb);
                if (p != null) {
                    orPredicates.add(p);
                }
            }
            if(!orPredicates.isEmpty()) {
                return cb.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
            }
        }else {
            return getPredicate(property, value, root, cb);
        }
        return null;
    }

    default Predicate specification(Relation relation, List<String> propertyName, List<String> propertyValue, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb){
        Predicate predicate = null;
        if(propertyName == null || propertyValue == null || propertyName.isEmpty() || propertyValue.isEmpty()){
            return null;
        }
        if(propertyName.size() > 1){
            if(propertyName.size() != propertyValue.size()){
                LOGGER.error("多属性查询错误, 参数和值不匹配, properties: {}, values: {}", propertyName, propertyValue);
                return null;
            }
            int size = Math.min(propertyName.size(), propertyValue.size());
            List<Predicate> predicates = new ArrayList<>();
            for(int i=0; i<size; i++){
                Predicate p = getCompoundPredicate(propertyName.get(i), propertyValue.get(i), root, cb);
                if(p != null){
                    predicates.add(p);
                }
            }
            if(relation == Relation.or) {
                predicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
            }else if(relation == Relation.and) {
                predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }else {
                predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }else {
            predicate = getCompoundPredicate(propertyName.get(0), propertyValue.get(0), root, cb);
        }
        return predicate;
    }

    default String getDoName(){
        return this.getClass().getSimpleName().replace("ServiceImpl", "");
    }

    Page<T> findByPropertyImpl(Relation relation, List<String> propertyName, List<String> propertyValue, int pageNumber, int pageSize, Sort.Direction direction, String... properties);

}
