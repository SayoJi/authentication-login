package com.sayo.authlogin.valueobject;

import java.util.List;

/**
 * Created by Shuangyao
 * 23:10 2018/9/6
 */
public class UserView {

    private String userName;
    private String userDesc;
    private List<String> roleCodes;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
