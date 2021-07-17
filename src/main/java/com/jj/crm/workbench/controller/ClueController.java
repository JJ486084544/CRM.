package com.jj.crm.workbench.controller;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.entity.User;
import com.jj.crm.settings.service.UserService;
import com.jj.crm.utils.DateTimeUtil;
import com.jj.crm.utils.UUIDUtil;
import com.jj.crm.workbench.entity.Activity;
import com.jj.crm.workbench.entity.Clue;
import com.jj.crm.workbench.entity.Tran;
import com.jj.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/24  - {TIME}
 */
@Controller
@RequestMapping("clue")
public class ClueController {
    @Resource
    private UserService userService;
    @Resource
    private ClueService clueService;


    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Result saveClue(Clue clue) {
        return clueService.saveClue(clue);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    public ModelAndView detailClue(String id) {
        Clue clue = clueService.detailClue(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject(clue);
        mv.setViewName("forward:/workbench/clue/detail.jsp");
        return mv;
    }

    @RequestMapping("/getActivityList.do")
    @ResponseBody
    public Result getActivityListByClueId(String clueId) {

        return clueService.getActivityListByClueId(clueId);
    }

    @RequestMapping("/unbund.do")
    @ResponseBody
    public Result unbund(String id) {

        return clueService.unbund(id);
    }

    @RequestMapping("/getActivityListByName.do")
    @ResponseBody
    public Result getActivityList(String name, String clueId) {

        return clueService.getActivityList(name, clueId);
    }

    @RequestMapping("/bond.do")
    @ResponseBody
    public Result bond(String cid, String[] aid) {
        return clueService.bond(cid, aid);
    }

    @RequestMapping("/searchActivityListByName.do")
    @ResponseBody
    public Result getActivityList(String name) {

        return clueService.searchActivityListByName(name);
    }

    @RequestMapping("/convert.do")
    @ResponseBody
    public ModelAndView convert(String clueId, String flag, Tran tran) {

        Result result = clueService.convert(clueId, flag, tran);
        ModelAndView mv = new ModelAndView();
        if (result.getSuccess()){
            mv.setViewName("forward:/workbench/clue/index.jsp");
        }
        return mv;

    }

}
