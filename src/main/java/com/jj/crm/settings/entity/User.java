package com.jj.crm.settings.entity;

import lombok.Data;

/**
 * @author 任人子
 * @date 2021/6/14  - {TIME}
 */
@Data
public class User {

      private String   id ;
      private String   loginAct;
      private String   name;
      private String   loginPwd;
      private String   email;
      private String   expireTime;
      private String   lockState;
      private String   deptno;
      private String   allowIps;
      private String   createTime;
      private String   createBy;
      private String   editTime;
      private String   editBy;


}
