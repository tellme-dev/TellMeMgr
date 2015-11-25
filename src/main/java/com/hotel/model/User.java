package com.hotel.model;

import java.util.Date;
import java.util.List;

public class User {
    private Integer id;

    private String name;

    private Integer orgId;

    private String psd;

    private Boolean isUsed;

    private Date createTime;

    private String salt;
    
  //自定义
    private String  createtime;
    
    private String  searchName;
    
    private String orgName;
    
    private int selectedMainMemu;
    
    private int selectedChildMenu;
    
    private List<Function> childMenuList;
    
    private boolean rememberMe;

    public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getSelectedMainMemu() {
		return selectedMainMemu;
	}

	public void setSelectedMainMemu(int selectedMainMemu) {
		this.selectedMainMemu = selectedMainMemu;
	}

	public int getSelectedChildMenu() {
		return selectedChildMenu;
	}

	public void setSelectedChildMenu(int selectedChildMenu) {
		this.selectedChildMenu = selectedChildMenu;
	}

	public List<Function> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<Function> childMenuList) {
		this.childMenuList = childMenuList;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
