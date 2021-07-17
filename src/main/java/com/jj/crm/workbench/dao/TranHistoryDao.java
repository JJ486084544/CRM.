package com.jj.crm.workbench.dao;

import com.jj.crm.workbench.entity.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getTranHistoryList(String tranId);
}
