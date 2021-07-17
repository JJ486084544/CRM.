package com.jj.crm.handle.listener;

import com.jj.crm.settings.entity.DicValue;
import com.jj.crm.settings.service.DicService;
import com.jj.crm.settings.service.impl.DicServiceImpl;
import com.jj.crm.utils.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * @author 任人子
 * @date 2021/7/12  - {TIME}
 */

public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        DicService dicService = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()).getBean(DicService.class);
        //System.out.println("上下文对象域创建了");
        ServletContext application = event.getServletContext();

        Map<String, List<DicValue>> map = dicService.getAll();

        Set<String> set =  map.keySet();
        for (String key :set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器处理数据字典完毕");


        Map<String,String> sMap = new HashMap<>();
        ResourceBundle rb  = ResourceBundle.getBundle("conf/stage2Possibility");
        Enumeration<String> enumeration =  rb.getKeys();
        while (enumeration.hasMoreElements()){
            String key  = enumeration.nextElement();
            String value = rb.getString(key);
            sMap.put(key,value);
        }
        application.setAttribute("sMap",sMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
