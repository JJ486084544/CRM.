package com.jj.crm.workbench.entity;

import lombok.Data;

/**
 * @author 任人子
 * @date 2021/6/19  - {TIME}
 */
@Data
public class Activity {
    private String   id;
    private String   owner;
    private String   name;
    private String   startDate;
    private String   endDate;
    private String   cost;
    private String   description;
    private String   createTime;
    private String   createBy;
    private String   editTime;
    private String   editBy;

}
