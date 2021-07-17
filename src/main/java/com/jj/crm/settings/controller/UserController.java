package com.jj.crm.settings.controller;

import com.jj.crm.basic.Result;
import com.jj.crm.settings.entity.User;
import com.jj.crm.settings.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 任人子
 * @date 2021/6/14  - {TIME}
 */
@RestController
@RequestMapping("user")
@SessionAttributes("user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/login.do")
    public Result selectUser(User user, HttpServletRequest request, Model model) {

        //获取访客的ip
        String ip = request.getRemoteAddr();
        Result result = userService.selectUser(user, ip);

        if (result.getSuccess()) {
            //查询存在,将user对象存在request作用域中
            model.addAttribute("user", (User) result.getData());
            return result;
        }
        return result;
    }

    @RequestMapping("/check.do")
    public String checkInterceptor() {
        return "验证拦截器的使用";
    }
}
