package com.jj.crm.workbench.dao;


import com.jj.crm.workbench.entity.Activity;
import com.jj.crm.workbench.entity.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueActivityRelationDao {


    List<Activity> getActivityListByClueId(String clueId);

    int unbund(String id);

    List<Activity> getActivityListByName(@Param("name") String name,@Param("clueId") String clueId);

    int bond(@Param("id") String id,@Param("cid") String cid,@Param("a") String a);

    List<ClueActivityRelation> getClueActivityRelationListByClueId(String clueId);

    int deleteClueActivityRelation(ClueActivityRelation clueActivityRelation);
}
