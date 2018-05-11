package com.example.useraccount.service;

import com.example.useraccount.model.UserAccount;
import com.example.useraccount.repository.UserAccountRepository;
import com.example.useraccount.utils.Relation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Transactional
@Service
public class UserAccountServiceImpl implements UserAccountService {
    protected Logger logger= LoggerFactory.getLogger(getClass());

    public Integer pageMaxSize = 4000;


    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Override
    public void addUser(String id, String name, Integer age) {
        jdbcTemplate.update("INSERT INTO USER(id,NAME, AGE) values(?, ?,?)", id, name, age);
    }

    @Override
    public Page<UserAccount> list(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public Page<UserAccount> list(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return null;
    }

    @Override
    public void deleteUserByid(String id) {
    }

    @Override
    public UserAccount findOne(String id) {
        return null;
    }

    @Override
    public List<UserAccount> findByNname(String name) {
        return null;
    }

    @Override
    public void save(UserAccount userAccount) {
        logger.info("userAccount={}",userAccount);
    }

    @Override
    public Page<UserAccount> findByPropertyImpl(Relation relation, List<String> propertyName, List<String> propertyValue, int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return  userAccountRepository.findAll(
                (root, query, cb) -> specification(relation, propertyName, propertyValue, root, query, cb),
                getPageRequest(pageNumber, pageSize, pageMaxSize, direction, properties)
        );
    }

    @Override
    public void maddUser(String id, String name, Integer age) {
    }
}
