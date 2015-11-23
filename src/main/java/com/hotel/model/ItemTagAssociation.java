package com.hotel.model;

public class ItemTagAssociation {
    private Integer id;

    private Integer itemId;

    private Integer itemTagId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemTagId() {
        return itemTagId;
    }

    public void setItemTagId(Integer itemTagId) {
        this.itemTagId = itemTagId;
    }
}