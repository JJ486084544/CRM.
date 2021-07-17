package com.jj.crm.settings.dao;

import com.jj.crm.settings.entity.DicValue;

import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/24  - {TIME}
 */
public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
