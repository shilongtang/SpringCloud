package com.example.eurekaclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    protected Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addUser(String id, String name, Integer age) {
        jdbcTemplate.update("INSERT INTO USER(id,NAME, AGE) values(?, ?,?)", id, name, age);
    }
}
