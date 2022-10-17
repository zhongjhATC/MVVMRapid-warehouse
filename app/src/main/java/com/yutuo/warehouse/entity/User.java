package com.yutuo.warehouse.entity;

/**
 * Created by zhongjh on 2021/3/25.
 * https://github.com/hongyangAndroid/wanandroid
 * wanandroid 用户
 */
public class User {

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
