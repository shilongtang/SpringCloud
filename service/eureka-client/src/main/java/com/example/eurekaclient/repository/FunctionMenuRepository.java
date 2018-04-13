package com.example.eurekaclient.repository;

import com.example.eurekaclient.model.FunctionMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionMenuRepository extends JpaRepository<FunctionMenu,String>,JpaSpecificationExecutor<FunctionMenu> {
}
