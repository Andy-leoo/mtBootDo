package com.bootdo.train.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Module implements Serializable {

    private Long id;
    private String title;
    private String detail;
    private String modules;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String creater;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operatoTime;

    private String  operator;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getOperatoTime() {
        return operatoTime;
    }

    public void setOperatoTime(Date operatoTime) {
        this.operatoTime = operatoTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }
}
