package com.example.useraccount.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
@Table(name = "user_account")
@Entity
@Component
public class UserAccount {

    @Id
    protected String id;
    @Column(length = 20)
    @Size(max=20, min=0, message = "account:用户账号 的 用户名 长度不能超过20")
    protected String account;
    @Column(length = 20)
    @Size(max=20, min=0, message = "name:用户账号的 用户姓名 长度不能超过20")
    protected String name;
    @Column(length = 11)
    @Size(max=11, min=0, message = "mobilePhone:用户账号的 手机号 长度不能超过11")
    protected String mobilePhone;
    @Column(length = 100)
    @Size(max=100, min=0, message = "phone:用户账号 的 联系电话 长度不能超过100")
    protected String phone;
    @Column(length = 50)
    @Size(max=50, min=0, message = "email:用户账号 的 电子邮箱 长度不能超过50")
    protected String email;
    @Column(length = 50)
    @Size(max=50, min=0, message = "passWord:用户账号 的 密码 长度不能超过50")
    protected String passWord;
    @Column
    protected Long status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(required = true,example = "2018-05-11 17:00:00")
    @Temporal(TemporalType.TIMESTAMP)
    protected java.util.Date createDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(required = true,example = "2018-05-11 17:00:00")
    @Temporal(TemporalType.TIMESTAMP)
    protected java.util.Date updateDate;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * 用户名
     */
    public String getAccount() {
        return account;
    }
    /**
     * 用户名
     */
    public void setAccount(String account) {
        this.account = account;
    }
    /**
     * 用户姓名
     */
    public String getName() {
        return name;
    }
    /**
     * 用户姓名
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 手机号
     */
    public String getMobilePhone() {
        return mobilePhone;
    }
    /**
     * 手机号
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    /**
     * 联系电话
     */
    public String getPhone() {
        return phone;
    }
    /**
     * 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * 电子邮箱
     */
    public String getEmail() {
        return email;
    }
    /**
     * 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * 密码
     */
    public String getPassWord() {
        return passWord;
    }
    /**
     * 密码
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * 状态
     */
    public Long getStatus() {
        return status;
    }
    /**
     * 状态
     */
    public void setStatus(Long status) {
        this.status = status;
    }
    /**
     * 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }
    /**
     * 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
