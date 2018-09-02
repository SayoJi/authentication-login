package com.sayo.authlogin;

import com.sayo.authlogin.domain.User;
import com.sayo.authlogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Shuangyao
 * 22:56 2018/9/2
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/user/{userName}")
    public User getUserByName(@PathVariable("userName") String userName){
        return userRepository.findByUserName(userName);
    }
}
