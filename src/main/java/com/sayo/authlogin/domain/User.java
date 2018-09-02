package com.sayo.authlogin.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Shuangyao
 * 21:45 2018/9/2
 */
@Entity
@Table(name = "user")
public class User {
    private String userName;
    private String userDescription;
    private String password;
    private List<UserRole> roles;

    @Id
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_desc")
    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    @JoinTable(name = "user_user_role",
            joinColumns = @JoinColumn(name = "user_name", referencedColumnName = "user_name", updatable = false, insertable = false),
            inverseJoinColumns = @JoinColumn(name = "role_code", referencedColumnName = "role_code", updatable = false, insertable = false))
    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
