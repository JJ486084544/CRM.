package com.jj.crm.workbench.dao;

import com.jj.crm.workbench.entity.ActivityRemark;

import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/19  - {TIME}
 */
public interface ActivityRemarkDao {
    int getCountByid(String[] id);

    int deleteByid(String[] id);

    List<ActivityRemark> showRemarkList(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    ActivityRemark showRemarkById(String id);

    int  editRemark(ActivityRemark activityRemark);
}
