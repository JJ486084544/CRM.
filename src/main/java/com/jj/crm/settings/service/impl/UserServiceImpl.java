package com.jj.crm.settings.service.impl;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.dao.UserDao;
import com.jj.crm.settings.entity.User;
import com.jj.crm.settings.service.UserService;
import com.jj.crm.utils.DateTimeUtil;
import com.jj.crm.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 任人子
 * @date 2021/6/14  - {TIME}
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public Result selectUser(User user, String ip) {
        //将密码明文转为MD5密文
        user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));

        user = userDao.selectUserById(user);
        //验证账号密码
        if (user == null){
            return Result.fail();
        }
        //验证实效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (currentTime.compareTo(expireTime) < 0){
            return Result.fail();
        }
        //验证锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            return Result.fail();
        }
        //验证ip地址
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            return Result.fail();
        }
        return Result.success(user);
    }
    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
}
