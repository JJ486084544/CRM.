package com.jj.crm.workbench.controller;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.entity.User;
import com.jj.crm.settings.service.UserService;
import com.jj.crm.workbench.entity.Activity;
import com.jj.crm.workbench.entity.ActivityRemark;
import com.jj.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 任人子
 * @date 2021/6/19  - {TIME}
 */
@Controller
@RequestMapping("activity")
public class ActivityController {

    @Resource
    private UserService userService;

    @Resource
    private ActivityService activityService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @RequestMapping("/saveActivity.do")
    @ResponseBody
    public Result saveActivity(Activity activity){
        return activityService.saveActivity(activity);
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public Result pageList(Activity activity, String pageNo, String pageSize){
        return activityService.pageList(activity,pageNo,pageSize);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Result deleteActivity(String [] id){
        return activityService.deleteActivity(id);
    }

    @RequestMapping("/getUserListAndActivity.do")
    @ResponseBody
    public Result getUserListAndActivity(String id){
        return activityService.getUserListAndActivity(id);
    }

    @RequestMapping("/updateActivity.do")
    @ResponseBody
    public Result updateActivity(Activity activity){
        return activityService.updateActivity(activity);
    }

    @RequestMapping("/detail.do")
    public ModelAndView detailActivity(String id){
        Activity activity = activityService.detailActivity(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject(activity);
        mv.setViewName("forward:/workbench/activity/detail.jsp");
        return mv;
    }

    @RequestMapping("/showRemarkList.do")
    @ResponseBody
    public Result showRemarkList(String activityId){
        return activityService.showRemarkList(activityId);
    }

    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    public Result deleteRemark(String id){
        return activityService.deleteRemark(id);
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Result saveRemark(ActivityRemark activityRemark){
        return activityService.saveRemark(activityRemark);
    }

    @RequestMapping("/editRemark.do")
    @ResponseBody
    public Result editRemark(ActivityRemark activityRemark){
        return activityService.editRemark(activityRemark);
    }
}
