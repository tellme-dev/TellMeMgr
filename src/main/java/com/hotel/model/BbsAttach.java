package com.hotel.model;

import java.util.Date;

public class BbsAttach {
    private Integer id;

    private Integer bbsId;

    private Integer attachType;

    private String attachUrl;

    private Date timeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBbsId() {
        return bbsId;
    }

    public void setBbsId(Integer bbsId) {
        this.bbsId = bbsId;
    }

    public Integer getAttachType() {
        return attachType;
    }

    public void setAttachType(Integer attachType) {
        this.attachType = attachType;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}