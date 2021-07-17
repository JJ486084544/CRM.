package com.jj.crm.workbench.service.impl;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.dao.UserDao;
import com.jj.crm.settings.entity.User;
import com.jj.crm.utils.DateTimeUtil;
import com.jj.crm.utils.UUIDUtil;
import com.jj.crm.workbench.dao.*;
import com.jj.crm.workbench.entity.*;
import com.jj.crm.workbench.service.ClueService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/24  - {TIME}
 */
@Service
public class ClueServiceImpl implements ClueService {
    //线索
    @Resource
    private ClueDao clueDao;
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;

    //客户
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;

    //联系人
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    //交易
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;

    //用户,市场活动
    @Resource
    private UserDao userDao;
    @Resource
    private ActivityDao activityDao;
    @Resource
    private HttpServletRequest request;


    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public Result saveClue(Clue clue) {
        //主键id,创建人,创建时间
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setCreateBy(((User) request.getSession().getAttribute("user")).getName());

        if (clueDao.saveClue(clue) > 0) {
            return Result.success();
        }
        return Result.fail();
    }

    @Override
    public Clue detailClue(String id) {
        if (id != null) {
            return clueDao.detailClueById(id);
        }
        return null;
    }

    @Override
    public Result getActivityListByClueId(String clueId) {
        List<Activity> list = clueActivityRelationDao.getActivityListByClueId(clueId);
        if (list == null) {
            return Result.fail();
        }
        return Result.success(list);

    }

    @Override
    public Result unbund(String id) {
        if (clueActivityRelationDao.unbund(id) > 0) {
            return Result.success();
        }
        return Result.fail();
    }

    @Override
    public Result getActivityList(@Param("name") String name, @Param("clueId") String clueId) {
        List<Activity> activityList = clueActivityRelationDao.getActivityListByName(name, clueId);
        if (activityList != null) {
            return Result.success(activityList);
        }
        return Result.fail();
    }

    @Override
    public Result bond(String cid, String[] aid) {
        int i = 0;
        for (String a : aid) {
            String id = UUIDUtil.getUUID();
            i += clueActivityRelationDao.bond(id, cid, a);
        }
        if (aid.length == i) {
            return Result.success();
        }


        return Result.fail();
    }

    @Override
    public Result searchActivityListByName(String name) {
        List<Activity> list = activityDao.searchActivityListByName(name);
        if (list == null) {
            return Result.fail();
        }
        return Result.success(list);

    }

    @Override
    public Result convert(String clueId, String flag, Tran tran) {
        Boolean flags = true;


        Tran t = null;
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();

        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue = clueDao.getClueById(clueId);

        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(company);
            customer.setDescription(clue.getDescription());
            customer.setContactSummary(clue.getContactSummary());
            //添加客户
            int count1 = customerDao.saveCustomer(customer);
            if (count1 != 1) {
                flags = false;
            }
        }
        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        int count2 = contactsDao.saveContacts(contacts);
        if (count2 != 1) {
            flags = false;
        }
        //(4) 线索备注转换到客户备注以及联系人备注
        //根据clued查询全部备注
        List<ClueRemark> clueRemarkList  = clueRemarkDao.getListByClueId(clueId);
        if (clueRemarkList != null){
            for (ClueRemark clueRemark : clueRemarkList){
                String noteContent =  clueRemark.getNoteContent();

                //创建客户对象，添加备注
                CustomerRemark customerRemark = new CustomerRemark();
                customerRemark.setId(UUIDUtil.getUUID());
                customerRemark.setNoteContent(noteContent);
                customerRemark.setCustomerId(customer.getId());
                customerRemark.setCreateBy(createBy);
                customerRemark.setCreateTime(createTime);
                customerRemark.setEditFlag("0");
                int count3 = customerRemarkDao.saveCustomerRemark(customerRemark);
                if (count3 != 1) {
                    flags = false;
                }
                //创建联系人对象，添加备注
                ContactsRemark contactsRemark = new ContactsRemark();
                contactsRemark.setId(UUIDUtil.getUUID());
                contactsRemark.setNoteContent(noteContent);
                contactsRemark.setContactsId(contacts.getId());
                contactsRemark.setCreateBy(createBy);
                contactsRemark.setCreateTime(createTime);
                contactsRemark.setEditFlag("0");
                int count4 = contactsRemarkDao.saveContactsRemark(contactsRemark);
                if (count4 != 1) {
                    flags = false;
                }

            }
        }
        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getClueActivityRelationListByClueId(clueId);
        if (clueActivityRelationList != null){
            for (ClueActivityRelation clueActivityRelation : clueActivityRelationList){
                String activityId = clueActivityRelation.getActivityId();
                ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
                contactsActivityRelation.setActivityId(activityId);
                contactsActivityRelation.setContactsId(contacts.getId());
                contactsActivityRelation.setId(UUIDUtil.getUUID());
                int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
                if (count5 != 1) {
                    flags = false;
                }
            }
        }

        //(6) 如果有创建交易需求，创建一条交易
        /*
        *
        * t对象已有信息:name,expectedDate,stage,activityId
        *
        * */
        //flag表示是否创建交易
        if ("a".equals(flag)) {
            t = tran;
            t.setId(UUIDUtil.getUUID());
            t.setCreateTime(createTime);
            t.setCreateBy(createBy);
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setDescription(clue.getDescription());
            t.setNextContactTime(clue.getNextContactTime());
            t.setContactsId(contacts.getId());
            t.setContactSummary(contacts.getContactSummary());
            t.setCustomerId(customer.getId());
            int count6 = tranDao.save(t);
            if (count6 != 1){
                flags = false;
            }
            //(7) 如果创建了交易，则创建一条该交易下的交易历史

            TranHistory tranHistory = new TranHistory();
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            tranHistory.setTranId(t.getId());
            int count7 = tranHistoryDao.save(tranHistory);
            if (count7 != 1){
                flags = false;
            }
        }
        //(8) 删除线索备注
        for (ClueRemark clueRemark : clueRemarkList){
            int count8 = clueRemarkDao.deleteClueRemark(clueRemark);
            if (count8 != 1){
                flags  = false;
            }
        }
        //(9) 删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList){
            int count9 = clueActivityRelationDao.deleteClueActivityRelation(clueActivityRelation);
            if (count9 != 1){
                flags  = false;
            }
        }
        //(10) 删除线索
        int count10 = clueDao.deleteClue(clueId);
        if (count10 != 1){
            flags  = false;
        }
        Result result = new Result();
        result.setSuccess(flags);
        return result;
    }
}
