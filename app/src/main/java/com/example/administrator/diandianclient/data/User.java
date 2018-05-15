package com.example.administrator.diandianclient.data;

/**
 * Created by Administrator on 2018/4/29.
 */

public class User {
    private String userNmae;
    private String phoneNumber;
    public User(String name,String phone){
        this.userNmae=name;
        this.phoneNumber=phone;
    }
    public String getUserNmae() {
        return userNmae;
    }

    public void setUserNmae(String userNmae) {
        this.userNmae = userNmae;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
