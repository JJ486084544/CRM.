package com.jj.crm.settings.service;

import com.jj.crm.settings.entity.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @author 任人子
 * @date 2021/6/24  - {TIME}
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
