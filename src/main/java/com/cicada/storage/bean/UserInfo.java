package com.cicada.storage.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class UserInfo implements Serializable {

    private Long id;
    /**
     * ldap账户
     */
    @JSONField(serialize = false)
    private String ldapNaming;
    /**
     * 所属机构
     */
    @JSONField(serialize = false)
    private String memberOf;
    /**
     * 用户邮箱
     */
    private String loginId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户邮箱密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 管理员类型 0 普通用户，1超级管理员
     */
    private Integer type;

    private Long createTime;

    private Long loginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLdapNaming() {
        return ldapNaming;
    }

    public void setLdapNaming(String ldapNaming) {
        this.ldapNaming = ldapNaming;
    }

    public String getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String memberOf) {
        this.memberOf = memberOf;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
}
