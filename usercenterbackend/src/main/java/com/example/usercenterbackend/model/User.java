package com.example.usercenterbackend.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @TableName user
 */
@Setter
@Getter
@TableName(value = "user")
public class User implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 账号
     */
    private String useraccount;
    /**
     * 用户头像
     */
    private String avatarurl;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 密码
     */
    private String userpassword;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态 0-正常
     */
    private Integer userstatus;
    /**
     * 电话
     */
    private String phone;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新时间
     */
    private Date updatetime;
    /**
     * 是否删除
     */
    private Integer isdelete;

    /**
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户昵称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户昵称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 账号
     */
    public String getUseraccount() {
        return useraccount;
    }

    /**
     * 账号
     */
    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    /**
     * 用户头像
     */
    public String getAvatarurl() {
        return avatarurl;
    }

    /**
     * 用户头像
     */
    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
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
     * 密码
     */
    public String getUserpassword() {
        return userpassword;
    }

    /**
     * 密码
     */
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
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
     * 状态 0-正常
     */
    public Integer getUserstatus() {
        return userstatus;
    }

    /**
     * 状态 0-正常
     */
    public void setUserstatus(Integer userstatus) {
        this.userstatus = userstatus;
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
     * 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 是否删除
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * 是否删除
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}