package com.hotel.model;

import java.util.List;

import com.hotel.common.utils.Page;

public class Org extends Page {
    private Integer id;

    private String name;

    private Integer parentId;

    private Integer level;

    private String path;

    private Integer hotelId;
    
    //自定义
    private List<Org> children;
    
    private String text;

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Org> getChildren() {
		return children;
	}

	public void setChildren(List<Org> children) {
		this.children = children;
	}

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
}