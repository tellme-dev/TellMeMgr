package com.hotel.model;

import com.hotel.common.utils.Page;

public class Menu extends Page {
    private Integer id;

    private String name;

    private Integer menuType;

    private Integer itemTagId;

    private Integer index;

    private String iconUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getItemTagId() {
        return itemTagId;
    }

    public void setItemTagId(Integer itemTagId) {
        this.itemTagId = itemTagId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}