package com.example.eurekaclient.service;

import com.example.eurekaclient.model.FunctionMenu;
import com.example.eurekaclient.repository.FunctionMenuRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FunctionMenuServiceImpl implements FunctionMenuService {


    public Integer pageMaxSize = 4000;

    @Autowired
    FunctionMenuRepository functionMenuRepository;

    @Override
    public Page<FunctionMenu> list(int pageNumber, int pageSize) {
        return functionMenuRepository.findAll(getPageRequest(pageNumber, pageSize, pageMaxSize));
    }

    @Override
    public Page<FunctionMenu> list(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return functionMenuRepository.findAll(getPageRequest(pageNumber, pageSize, pageMaxSize, direction, properties));
    }

    @Override
    public FunctionMenu save(FunctionMenu functionMenu) {
        if(StringUtils.isBlank(functionMenu.getId())){
            functionMenu.setId(UUID.randomUUID().toString());
        }
        return functionMenuRepository.save(functionMenu);
    }

    @Override
    public void deleteUserByid(String id) {
        functionMenuRepository.delete(id);
    }

    @Override
    public Page<FunctionMenu> list1(Integer pageNumber, Integer pageSize) {
        return this.list(pageNumber,pageSize);
    }

    @Override
    public Page<FunctionMenu> findByPropertyImpl(Relation relation, List<String> propertyName, List<String> propertyValue, int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return  functionMenuRepository.findAll(
                (root, query, cb) -> specification(relation, propertyName, propertyValue, root, query, cb),
                getPageRequest(pageNumber, pageSize, pageMaxSize, direction, properties)
        );
    }
    @Override
    public FunctionMenu findOne(String id) {
        return functionMenuRepository.findOne(id);
    }
}
