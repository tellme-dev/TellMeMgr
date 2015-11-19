package com.hotel.model;

import com.hotel.common.utils.Page;

public class ItemTag extends Page {
    private Integer id;

    private String name;

    private String note;

    private Integer tagType;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}
}