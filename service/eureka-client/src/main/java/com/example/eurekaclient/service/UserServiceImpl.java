package com.example.eurekaclient.service;

import com.example.eurekaclient.model.User;
import com.example.eurekaclient.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    protected Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(String id, String name, Integer age) {
        jdbcTemplate.update("INSERT INTO USER(id,NAME, AGE) values(?, ?,?)", id, name, age);
    }


    @Override
    public void deleteUserByid(String id) {
        userRepository.delete(id);
    }

    @Override
    public User findOne(String id) {
        User one = userRepository.findOne(id);
        return one;
    }

    @Override
    public  List<User> findByNname(String name) {
        List<User> user = userRepository.findByName(name);
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Page<User> findByPropertyImpl(Relation relation, List<String> propertyName, List<String> propertyValue, int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
       /* return  userRepository
                .findAll(
                        (root, query, cb) -> specification(relation, propertyName, propertyValue, root, query, cb),
                        getPageRequest(pageNumber, pageSize, pageMaxSize, direction, properties)
                );*/
       return null;
    }


}
