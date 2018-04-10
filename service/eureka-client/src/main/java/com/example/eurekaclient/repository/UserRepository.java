package com.example.eurekaclient.repository;

import com.example.eurekaclient.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> ,JpaSpecificationExecutor<User> {
    List<User> findByName(String name);

}
