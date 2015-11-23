package com.hotel.model;

public class BannerDetail {
    private Integer id;

    private Integer adId;

    private Integer sort;

    private Integer bannerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer index) {
        this.sort = index;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }
}