package com.jj.crm.workbench.service;


import com.jj.crm.basic.Result;
import com.jj.crm.settings.entity.User;
import com.jj.crm.workbench.entity.Tran;

import java.util.List;

/**
 * @author 任人子
 * @date 2021/7/14  - {TIME}
 */

public interface TranService {
    List<User> getUserList();

    Result getCustomerName(String name);

    Result saveTran(String customerName,Tran tran);

    Result getTranById(String id);

    Result getTranHistoryList(String tranId);

    Result changeStage(Tran tran);

    Result getChars();
}
