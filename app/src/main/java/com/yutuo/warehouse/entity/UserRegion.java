package com.yutuo.warehouse.entity;

/**
 * Created by zhongjh on 2021/3/25.
 * 用于注册用户接口的实体类
 */
public class UserRegion {

    public UserRegion(String username, String password, String repassword) {
        this.username = username;
        this.password = password;
        this.repassword = repassword;
    }

    private String username;
    private String password;
    private String repassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

}
