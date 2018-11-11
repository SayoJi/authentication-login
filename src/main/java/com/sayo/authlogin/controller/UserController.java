package com.sayo.authlogin.controller;

import com.sayo.authlogin.service.UserService;
import com.sayo.authlogin.valueobject.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Shuangyao
 * 22:56 2018/9/2
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    @PreAuthorize("hasAuthority('admin')")
    public UserView getUserByName(@RequestParam("userName") String userName) {
        return userService.getUserByUserName(userName);
    }
}
