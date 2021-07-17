package com.jj.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.dao.UserDao;
import com.jj.crm.settings.entity.User;
import com.jj.crm.utils.DateTimeUtil;
import com.jj.crm.utils.UUIDUtil;
import com.jj.crm.workbench.dao.ActivityDao;
import com.jj.crm.workbench.dao.ActivityRemarkDao;
import com.jj.crm.workbench.entity.Activity;
import com.jj.crm.workbench.entity.ActivityRemark;
import com.jj.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 任人子
 * @date 2021/6/19  - {TIME}
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;

    @Resource
    private HttpServletRequest request;

    @Resource
    private ActivityRemarkDao activityRemarkDao;

    @Resource
    private UserDao userDao;

    @Override
    public Result saveActivity(Activity activity) {

        //id,createTime,createBy
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User) request.getSession().getAttribute("user")).getName());

        int result = activityDao.saveActivity(activity);
        if (result > 0) {
            return Result.success();
        }
        return Result.fail();
    }

    @Override
    public Result pageList(Activity activity, String pageNo, String pageSize) {

        Integer totalSize = activityDao.getTotalSizeByCondition(activity);
        System.out.println("totalSize=" + totalSize);
        PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
        List<Activity> activityList = activityDao.pageList(activity);

        return Result.pageList(totalSize, activityList);
    }

    @Override
    public Result deleteActivity(String[] id) {

        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByid(id);
        //删除备注,返回受到影响的条数(实际删除的数量)
        int count2 = activityRemarkDao.deleteByid(id);
        //删除市场活动
        Result result = Result.fail();
        result.setMessage("删除市场活动失败！");
        if (count1 != count2) {
            return result;
        }
        int count3 = activityDao.deleteActivity(id);
        if (count3 != id.length) {
            return result;
        }

        return Result.success();
    }

    @Override
    public Result getUserListAndActivity(String id) {
        //获取用户列表
        List<User> userList = userDao.getUserList();
        //获取市场活动
        Activity activity = activityDao.getActivityById(id);

        Map<Object, Object> UserListAndActivity = new HashMap<>();
        UserListAndActivity.put("userList", userList);
        UserListAndActivity.put("activity", activity);
        return Result.success(UserListAndActivity);
    }

    @Override
    public Result updateActivity(Activity activity) {

        //id,createTime,createBy
        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(((User) request.getSession().getAttribute("user")).getName());

        int result = activityDao.updateActivity(activity);
        if (result > 0) {
            return Result.success();
        }
        return Result.fail();
    }

    @Override
    public Activity detailActivity(String id) {
        return activityDao.detailActivity(id);
    }

    @Override
    public Result showRemarkList(String activityId) {
        return Result.success(activityRemarkDao.showRemarkList(activityId));
    }

    @Override
    public Result deleteRemark(String id) {
        if (activityRemarkDao.deleteRemark(id) > 0) {
            return Result.success();
        }
        return Result.fail();
    }

    @Override
    public Result saveRemark(ActivityRemark activityRemark) {
        //设置CreatBy
        activityRemark.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        //设置UUId
        String id = UUIDUtil.getUUID();
        activityRemark.setId(id);
        //设置createTime,editFlag
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("0");

        if (activityRemarkDao.saveRemark(activityRemark) > 0) {
            return Result.success(activityRemarkDao.showRemarkById(id));
        }
        return Result.fail();
    }

    @Override
    public Result editRemark(ActivityRemark activityRemark) {

        //获取editTime,editBy,修改editFlag
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        activityRemark.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        activityRemark.setEditFlag("1");

        if (activityRemarkDao.editRemark(activityRemark) > 0) {
            return Result.success(activityRemarkDao.showRemarkById(activityRemark.getId()));
        }
        return Result.fail();
    }
}
