package com.hotel.modelVM;

import java.util.Date;

import com.hotel.model.Customer;

public class CustomerVM {
    private Integer id;

    private String name;

    private String mobile;

    private Integer sex;

    private Date birthday;

    private String psd;

    private String photoUrl;

    private Date regTime;

    private Integer userId;

    private String salt;
    
    private int countAlways;
    private int countBrowse;
    private int countCollection;
    private int countTopic;
    private int countDynamic;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

	public int getCountAlways() {
		return countAlways;
	}

	public void setCountAlways(int countAlways) {
		this.countAlways = countAlways;
	}

	public int getCountBrowse() {
		return countBrowse;
	}

	public void setCountBrowse(int countBrowse) {
		this.countBrowse = countBrowse;
	}

	public int getCountCollection() {
		return countCollection;
	}

	public void setCountCollection(int countCollection) {
		this.countCollection = countCollection;
	}

	public int getCountTopic() {
		return countTopic;
	}

	public void setCountTopic(int countTopic) {
		this.countTopic = countTopic;
	}

	public int getCountDynamic() {
		return countDynamic;
	}

	public void setCountDynamic(int countDynamic) {
		this.countDynamic = countDynamic;
	}
	
	public void setCustomer(Customer customer){
		this.id = customer.getId();
		this.name = customer.getName();
		this.mobile = customer.getMobile();
		this.sex = customer.getSex();
		this.birthday = customer.getBirthday();
		this.psd = customer.getPsd();
		this.photoUrl = customer.getPhotoUrl();
		this.regTime = customer.getRegTime();
		this.userId = customer.getUserId();
		this.salt = customer.getSalt();
	}
}
