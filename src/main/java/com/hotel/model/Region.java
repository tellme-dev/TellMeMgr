package com.hotel.model;

public class Region {
    private Integer id;

    private Integer parentId;

    private Integer level;

    private String path;

    private String name;
    
    private String code;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Region() {
		// TODO Auto-generated constructor stub
	}
    
    public Region(boolean empty) {
		if(empty){
			this.id = 0;
			this.name = "";
		}
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
