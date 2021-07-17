package com.jj.crm.settings.dao;

import com.jj.crm.settings.entity.User;

import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/14  - {TIME}
 */

public interface UserDao {

    User selectUserById(User user);

    List<User> getUserList();
}
