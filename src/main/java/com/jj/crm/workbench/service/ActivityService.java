package com.jj.crm.workbench.service;

import com.jj.crm.basic.Result;
import com.jj.crm.workbench.entity.Activity;
import com.jj.crm.workbench.entity.ActivityRemark;


/**
 * @author 任人子
 * @date 2021/6/19  - {TIME}
 */
public interface ActivityService {

    Result saveActivity(Activity activity);

    Result pageList(Activity activity, String pageNo, String pageSize);

    Result deleteActivity(String[] ids);

    Result getUserListAndActivity(String id);

    Result updateActivity(Activity activity);

    Activity detailActivity(String id);

    Result showRemarkList(String activityId);

    Result deleteRemark(String id);

    Result saveRemark(ActivityRemark activityRemark);

    Result editRemark(ActivityRemark activityRemark);
}
