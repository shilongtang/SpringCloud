package com.example.eurekaclient.service;

import com.example.eurekaclient.model.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends BaseService<User> {
   void addUser(String id,String name,Integer age);

   void save(User user);

   List<User> findByNname(String name);

   void deleteUserByid(String id);

   User findOne(String id);

   void maddUser(String id,String name,Integer age);
}
