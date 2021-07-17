package com.jj.crm.workbench.service.impl;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.dao.UserDao;
import com.jj.crm.settings.entity.User;
import com.jj.crm.utils.DateTimeUtil;
import com.jj.crm.utils.UUIDUtil;
import com.jj.crm.workbench.dao.CustomerDao;
import com.jj.crm.workbench.dao.TranDao;
import com.jj.crm.workbench.dao.TranHistoryDao;
import com.jj.crm.workbench.entity.Customer;
import com.jj.crm.workbench.entity.Tran;
import com.jj.crm.workbench.entity.TranHistory;
import com.jj.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 任人子
 * @date 2021/7/14  - {TIME}
 */
@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Resource
    private UserDao userDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private HttpServletRequest request;

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public Result getCustomerName(String name) {
        return Result.success(customerDao.getCustomerName(name));
    }

    @Override
    public Result saveTran(String customerName,Tran tran) {
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Customer customer = customerDao.getCustomerByName(customerName);
        String customerId = null;
        if (customer != null){
            customerId = customer.getId();
        }
        else {
            customer = new Customer();
            customerId = UUIDUtil.getUUID();
            customer.setId(customerId);
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            customer.setName(customerName);
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setOwner(tran.getOwner());
            if (customerDao.saveCustomer(customer) == 0){
                return Result.fail();
            }
        }

        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(createTime);
        tran.setCreateBy(createBy);
        tran.setCustomerId(customerId);
        if (tranDao.save(tran) == 0){
            return Result.fail();
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(createTime);
        tranHistory.setCreateBy(createBy);
        if (tranHistoryDao.save(tranHistory) == 0){
            return Result.fail();
        }
        return Result.success();
    }

    @Override
    public Result getTranById(String id) {
        return Result.success(tranDao.getTranById(id));
    }

    @Override
    public Result getTranHistoryList(String tranId) {
        List<TranHistory> list = new ArrayList<>();
        List<TranHistory> tranHistoryList = tranHistoryDao.getTranHistoryList(tranId);
        Map<String,String> map = (Map<String, String>)  request.getServletContext().getAttribute("sMap");
        if (tranHistoryList !=null){
            for (TranHistory tranHistory : tranHistoryList){
                String stage = tranHistory.getStage();
                tranHistory.setPossibility(map.get(stage));
                list.add(tranHistory);
            }
        }
        return Result.success(list);
    }

    @Override
    public Result changeStage(Tran tran) {
        String time = DateTimeUtil.getSysTime();
        String by   = ((User)request.getSession().getAttribute("user")).getName();
        tran.setEditTime(time);
        tran.setEditBy(by);
        Map<String,String> map = (Map<String, String>)  request.getServletContext().getAttribute("sMap");
        tran.setPossibility(map.get(tran.getStage()));
        if (tranDao.changStage(tran) == 0){
            return Result.fail();
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(by);
        tranHistory.setCreateTime(time);
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setTranId(tran.getId());
        if (tranHistoryDao.save(tranHistory) == 0){
            return Result.fail();
        }
        return Result.success(tran);
    }

    @Override
    public Result getChars() {
        // 取得total
        int total = tranDao.getTotal();

        // 取得dataList
        List<Map<String,Object>> dataList = tranDao.getChars();

        // 将total和dataList保存到map中
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("total",total);
        map.put("dataList",dataList);

        return Result.success(map);
    }
}
