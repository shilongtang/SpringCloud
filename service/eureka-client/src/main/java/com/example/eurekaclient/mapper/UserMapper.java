package com.example.eurekaclient.mapper;

import com.example.eurekaclient.model.User;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findByName(@Param("name") String name);
    @Insert("INSERT INTO USER( ID, NAME, AGE) VALUES(#{id},#{name}, #{age})")
    int insert(@Param("id")String id, @Param("name") String name, @Param("age") Integer age);

    @Insert("INSERT INTO USER( ID, NAME, AGE) VALUES(#{id},#{name}, #{age})")
    int insertObject(User user);
    @Insert("INSERT INTO USER( ID, NAME, AGE) VALUES(#{id},#{name}, #{age})")
    int insertMap(Map<String, Object> map);

    @Select("SELECT * FROM USER a where a.name = #{name}")
    Map findByname(@Param("name") String name);

    @Delete("delete FROM USER WHERE ID = #{id}")
    void delete(String id);
    @Update("UPDATE USER SET NAME = #{name}, AGE = #{age} WHERE ID = #{id}")
    void update(User user);
}
