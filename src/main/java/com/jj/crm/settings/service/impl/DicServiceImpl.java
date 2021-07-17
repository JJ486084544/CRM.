package com.jj.crm.settings.service.impl;

import com.jj.crm.settings.dao.DicTypeDao;
import com.jj.crm.settings.dao.DicValueDao;
import com.jj.crm.settings.entity.DicType;
import com.jj.crm.settings.entity.DicValue;
import com.jj.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 任人子
 * @date 2021/6/24  - {TIME}
 */
@Service
public class DicServiceImpl implements DicService {
    @Resource
    private DicTypeDao dicTypeDao;
    @Resource
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String ,List<DicValue>> map = new HashMap<>();

        List<DicType> dicTypes = dicTypeDao.getTypeList();
        for (DicType dicType : dicTypes){
            String code = dicType.getCode();
            List<DicValue> dicValueList = dicValueDao.getListByCode(code);
            map.put(code,dicValueList);
        }
        return map;
    }
}
