package com.hotel.model;

import java.util.Date;

public class Banner {
    private Integer id;

    private Byte positionType;

    private Boolean isUsed;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getPositionType() {
        return positionType;
    }

    public void setPositionType(Byte positionType) {
        this.positionType = positionType;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}