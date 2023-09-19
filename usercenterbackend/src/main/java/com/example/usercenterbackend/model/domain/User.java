package com.example.usercenterbackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value = "user")
public class User implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String userAccount;
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 描述
     */
    private String description;

    /**
     * 性别
     */
    private Integer gender;
    /**
     * 粉丝数
     */
    private Integer fans;
    /**
     * 星球编号
     */
    private String planetCode;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;

    /**
     * 状态 0-正常
     */
    private Integer userStatus;
    /**
     * 角色身份
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 用户id
     */
    public Long getId() {
        return id;
    }

    /**
     * 用户id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 账号
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 账号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 密码
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 密码
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 昵称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 昵称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 头像
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 头像
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 粉丝数
     */
    public Integer getFans() {
        return fans;
    }

    /**
     * 粉丝数
     */
    public void setFans(Integer fans) {
        this.fans = fans;
    }

    /**
     * 性别
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 性别
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 星球编号
     */
    public String getPlanetCode() {
        return planetCode;
    }

    /**
     * 星球编号
     */
    public void setPlanetCode(String planetCode) {
        this.planetCode = planetCode;
    }

    /**
     * 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 角色身份
     */
    public Integer getUserRole() {
        return userRole;
    }

    /**
     * 角色身份
     */
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    /**
     * 状态 0-正常
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 状态 0-正常
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 是否删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}