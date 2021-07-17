package com.jj.crm.workbench.dao;

import com.jj.crm.workbench.entity.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/19  - {TIME}
 */
public interface ActivityDao {

    int saveActivity(Activity activity);

    Integer getTotalSizeByCondition(Activity activity);

    List<Activity> pageList(Activity activity);

    int deleteActivity(String[] id);

    Activity getActivityById(String id);

    int updateActivity(Activity activity);

    Activity detailActivity(String id);


    List<Activity> searchActivityListByName(@Param("name") String name);
}
