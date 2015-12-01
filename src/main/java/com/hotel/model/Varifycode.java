package com.hotel.model;

import java.util.Date;

public class Varifycode {
    private Integer id;

    private String mobile;

    private String varifyCode;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVarifyCode() {
        return varifyCode;
    }

    public void setVarifyCode(String varifyCode) {
        this.varifyCode = varifyCode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}