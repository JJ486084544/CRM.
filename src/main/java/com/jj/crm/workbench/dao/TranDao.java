package com.jj.crm.workbench.dao;

import com.jj.crm.basic.Result;
import com.jj.crm.workbench.entity.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);


    Tran getTranById(String id);

    int changStage(Tran tran);

    int getTotal();

    List<Map<String, Object>> getChars();
}
