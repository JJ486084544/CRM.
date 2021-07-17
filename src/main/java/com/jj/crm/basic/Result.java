package com.jj.crm.basic;

import org.springframework.stereotype.Component;

/**
 * @author 任人子
e 2021/6/18  - {TIME}
 */
public class Result {
    private Boolean success;
    private String message;
    private Object data;
    private Integer totalSize;

    public Result() {

    }

    public Result(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    public Result(Integer totalSize,Object data) {
        this.totalSize = totalSize;
        this.data = data;
    }
    public static Result success() {
        return new Result(true, null, null);
    }

    public static Result success(Object data) {
        return new Result(true, "登录成功", data);
    }

    public static Result fail() { return new Result(false, "登录失败", null); }

    public static Result pageList(Integer totalSize,Object data){
        return new Result(totalSize,data);
    }

   


    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
