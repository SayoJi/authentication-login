package com.sayo.authlogin.service;

import com.sayo.authlogin.domain.User;
import com.sayo.authlogin.domain.UserRole;
import com.sayo.authlogin.repository.UserRepository;
import com.sayo.authlogin.valueobject.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuangyao
 * 23:08 2018/9/6
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserView getUserByUserName(String userName){

        UserView userView = new UserView();
        User user = userRepository.findByUserName(userName);
        userView.setUserName(user.getUserName());
        userView.setUserDesc(user.getUserDescription());
        List<String> roleCodes = new ArrayList<>();
        user.getRoles().stream().forEach(role -> roleCodes.add(role.getRoleCode()));
        userView.setRoleCodes(roleCodes);
        return userView;
    }
}
