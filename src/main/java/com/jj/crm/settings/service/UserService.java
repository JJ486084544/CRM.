package com.jj.crm.settings.service;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.entity.User;

import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/14  - {TIME}
 */
public interface UserService {


    Result selectUser(User user, String ip);

    List<User> getUserList();
}
