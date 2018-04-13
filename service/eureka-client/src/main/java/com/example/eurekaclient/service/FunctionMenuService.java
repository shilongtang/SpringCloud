package com.example.eurekaclient.service;

import com.example.eurekaclient.model.FunctionMenu;
import com.example.eurekaclient.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FunctionMenuService extends BaseService<FunctionMenu> {

    FunctionMenu save(FunctionMenu functionMenu);

    void deleteUserByid(String id);

    Page<FunctionMenu> list1(Integer pageNumber, Integer pageSize);

    FunctionMenu findOne(String id);

}
