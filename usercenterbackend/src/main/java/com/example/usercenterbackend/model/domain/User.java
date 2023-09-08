package com.example.usercenterbackend.model.domain;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableLogic
    private Integer isdelete;

}