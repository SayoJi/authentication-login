package com.sayo.authlogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * This service will reload user information from database.
 */
@Service("databaseUserDetailService")
public class DatabaseUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        return this.userService.getUserDetailByUserName(userName);
    }
}
