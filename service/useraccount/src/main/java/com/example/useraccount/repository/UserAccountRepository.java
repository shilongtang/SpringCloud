package com.example.useraccount.repository;

import com.example.useraccount.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,String> ,JpaSpecificationExecutor<UserAccount> {
    List<UserAccount> findByName(String name);

}
