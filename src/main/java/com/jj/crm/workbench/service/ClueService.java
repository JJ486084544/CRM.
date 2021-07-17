package com.jj.crm.workbench.service;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.entity.User;
import com.jj.crm.workbench.entity.Clue;
import com.jj.crm.workbench.entity.Tran;

import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/24  - {TIME}
 */
public interface ClueService {
    List<User> getUserList();

    Result saveClue(Clue clue);

    Clue detailClue(String id);

    Result getActivityListByClueId(String clueId);

    Result unbund(String id);

    Result getActivityList(String name,String clueId);

    Result bond(String cid,String [] aid);

    Result searchActivityListByName(String name);

    Result convert(String clueId, String flag, Tran tran);
}
