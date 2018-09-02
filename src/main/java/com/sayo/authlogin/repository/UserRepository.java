package com.sayo.authlogin.repository;

import com.sayo.authlogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Shuangyao
 * 21:51 2018/9/2
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserName(String userName);

    @Query(value = "select r.roleCode from User u inner join u.roles as r where u.userName = :userName")
    List<String> queryUserOwnedRoleCodes(@Param(value = "userName") String userName);
}
