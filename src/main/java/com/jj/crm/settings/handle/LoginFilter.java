package com.jj.crm.settings.handle;


import com.jj.crm.settings.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();

        // 不应该被拦截的资源,自动放行请求
        if("/login.jsp".equals(path) || "/user/login.do".equals(path)){

            filterChain.doFilter(servletRequest,servletResponse);

        }else{

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            // 如果user不为null,说明登录过
            if(user != null){

                filterChain.doFilter(servletRequest,servletResponse);

                // 没有登录过
            }else{

                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }

        }
    }

    @Override
    public void destroy() {

    }

}
