package com.jj.crm.settings.handle;

import com.jj.crm.settings.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 任人子
 * @date 2021/6/15  - {TIME}
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    //拦截没有登录过的请求
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getServletPath();

        // 不应该被拦截的资源,自动放行请求
        if ("/login.jsp".equals(path) || "/user/login.do".equals(path)) {
            System.out.println("登入成功");
            return true;
        } else {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null){
                return true;
            }
            else {
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                return false;
            }
        }


    }

}
