package com.sayo.authlogin.service;

import com.sayo.authlogin.domain.User;
import com.sayo.authlogin.domain.UserRole;
import com.sayo.authlogin.repository.UserRepository;
import com.sayo.authlogin.valueobject.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Get {@link UserDetails} by user name.
     * @return
     */
    @Transactional
    public UserDetails getUserDetailByUserName(String username){

        User user = this.userRepository.findByUserName(username);

        if(user == null){
            //throw exception inform front end not this user
            throw new UsernameNotFoundException("user + " + username + "not found.");
        }

        List<String> roleList = this.userRepository.queryUserOwnedRoleCodes(username);
        List<GrantedAuthority> authorities = roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails
                .User(username,user.getPassword(),authorities);
    }
}
