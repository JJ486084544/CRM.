package com.jj.crm.workbench.entity;

import lombok.Data;

/**
 * @author 任人子
 * @date 2021/6/19  - {TIME}
 */
@Data
public class ActivityRemark {

  private String  id;
  private String  noteContent;
  private String  createTime;
  private String  createBy;
  private String  editTime;
  private String  editBy;
  private String  editFlag;
  private String  activityId;


}
