package com.example.eurekaclient.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    /**
     *  unique属性表示该字段是否为唯一标识，默认为false。如果表中有一个字段需要唯一标识，则既可以使用该标记，也可以使用@Table标记中的@UniqueConstraint。
     *  nullable属性表示该字段是否可以为null值，默认为true*
     *  insertable属性表示在使用“INSERT”脚本插入数据时，是否需要插入该字段的值*
     *  updatable属性表示在使用“UPDATE”脚本插入数据时，是否需要更新该字段的值。insertable和updatable属性一般多用于只读的属性，例如主键和外键等。这些字段的值通常是自动生成的*
     *  columnDefinition属性表示创建表时，该字段创建的SQL语句，一般用于通过Entity生成表定义时使用*
     *  table属性表示当映射多个表时，指定表的表中的字段。默认值为主表的表名。有关多个表的映射将在本章的5.6小节中详细讲述*
     *  length属性表示字段的长度，当字段的类型为varchar时，该属性才有效，默认为255个字符*
     *  precision属性和scale属性表示精度，当字段类型为double时，precision表示数值的总长度，scale表示小数点所占的位数。
     *
     */

    @Id
    public String id;
    @Column(length = 35,nullable = false)
    public String name;
    @Column(length = 1)
    public Integer age;

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
