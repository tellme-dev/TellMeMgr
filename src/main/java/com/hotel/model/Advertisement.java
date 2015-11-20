package com.hotel.model;

import java.util.Date;
import java.util.List;

import com.hotel.common.utils.Page;

public class Advertisement extends Page {
    private Integer id;

    private String name;

    private String key;

    private Byte targetType;

    private Integer targetId;

    private String targetContent;

    private Boolean isUsed;

    private Date createTime;

    private Date timeStamp;
    
    //自定义
    private String  createtime;
    
    private String targetName;
    
    private List<Advertisement> targetIds;
    
    private String imageUrl;
    
    private List<Advertisement> imageUrlList;
    
    private String imageText;
    
    private List<Advertisement> imageTextList;
    
    private AdDetail adDetail;
    
    private List<AdDetail> adDetailList;

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public List<AdDetail> getAdDetailList() {
		return adDetailList;
	}

	public void setAdDetailList(List<AdDetail> adDetailList) {
		this.adDetailList = adDetailList;
	}

	public List<Advertisement> getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(List<Advertisement> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	public List<Advertisement> getImageTextList() {
		return imageTextList;
	}

	public void setImageTextList(List<Advertisement> imageTextList) {
		this.imageTextList = imageTextList;
	}

	public AdDetail getAdDetail() {
		return adDetail;
	}

	public void setAdDetail(AdDetail adDetail) {
		this.adDetail = adDetail;
	}

	public List<Advertisement> getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(List<Advertisement> targetIds) {
		this.targetIds = targetIds;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageText() {
		return imageText;
	}

	public void setImageText(String imageText) {
		this.imageText = imageText;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Byte getTargetType() {
        return targetType;
    }

    public void setTargetType(Byte targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(String targetContent) {
        this.targetContent = targetContent;
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}