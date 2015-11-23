package com.hotel.model;

import java.util.List;

public class Function {
    private Integer id;

    private String name;

    private Integer parentId;

    private Byte level;

    private String path;
    
    private String url;

    private Boolean isLeaf;
    
    private List<Function> childFunctionlist;

    public List<Function> getChildFunctionlist() {
		return childFunctionlist;
	}

	public void setChildFunctionlist(List<Function> childFunctionlist) {
		this.childFunctionlist = childFunctionlist;
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

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}