package com.jj.crm.workbench.dao;

import com.jj.crm.workbench.entity.ClueRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getListByClueId(@Param("clueId") String clueId);

    int deleteClueRemark(ClueRemark clueRemark);
}
