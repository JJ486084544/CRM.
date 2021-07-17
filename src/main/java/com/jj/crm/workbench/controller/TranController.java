package com.jj.crm.workbench.controller;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.entity.User;
import com.jj.crm.workbench.entity.Clue;
import com.jj.crm.workbench.entity.Tran;
import com.jj.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 任人子
 * @date 2021/7/14  - {TIME}
 */
@Controller
@RequestMapping("transaction")
public class TranController {
    @Resource
    private TranService tranService;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("/goDetail.do")
    @ResponseBody
    public ModelAndView getUserList() {
        List<User> userList = tranService.getUserList();
        ModelAndView mv = new ModelAndView();
        mv.addObject(userList);
        mv.setViewName("forward:/workbench/transaction/save.jsp");
        return mv;
    }

    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public Result getCustomerName(String name) {
        return tranService.getCustomerName(name);
    }

    @RequestMapping("/saveTran.do")
    @ResponseBody
    public ModelAndView saveTran(String customerName,Tran tran) {

        Result result = tranService.saveTran(customerName,tran);
        ModelAndView mv = new ModelAndView();
        if (result.getSuccess()){
            mv.setViewName("forward:/workbench/transaction/index.jsp");
        }
        return mv;

    }

    @RequestMapping("/detail.do")
    @ResponseBody
    public ModelAndView getTranById(String id) {

        Result result = tranService.getTranById(id);
        ModelAndView mv = new ModelAndView();
        if (result.getSuccess()){
            Tran tran = (Tran) result.getData();
            mv.addObject(tran);
            mv.setViewName("forward:/workbench/transaction/detail.jsp");
        }
        return mv;

    }

    @RequestMapping("/getTranHistoryList.do")
    @ResponseBody
    public Result getTranHistoryList(String tranId) {
        return tranService.getTranHistoryList(tranId);
    }

    @RequestMapping("/changeStage.do")
    @ResponseBody
    public Result changeStage(Tran tran) {
        return tranService.changeStage(tran);
    }



    @RequestMapping("/getChars.do")
    @ResponseBody
    public Result getChars() {
        return tranService.getChars();
    }
}
