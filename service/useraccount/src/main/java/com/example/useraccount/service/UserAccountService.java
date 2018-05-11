package com.example.useraccount.service;

import com.example.useraccount.model.UserAccount;

import java.util.List;

public interface UserAccountService extends BaseService<UserAccount> {
   void addUser(String id, String name, Integer age);

   void save(UserAccount user);

   List<UserAccount> findByNname(String name);

   void deleteUserByid(String id);

   UserAccount findOne(String id);

   void maddUser(String id, String name, Integer age);
}
