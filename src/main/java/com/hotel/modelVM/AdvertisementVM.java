package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.AdDetail;
import com.hotel.model.Advertisement;


public class AdvertisementVM extends Advertisement{
	private List<AdDetail> adDetails;
	
	private Integer agreeCount;
	
	private Integer collectionCount;
	
	private Integer answerCount;
	
	private Boolean isAgreed;
	
	private Boolean isCollected;

	public Boolean getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(Boolean isCollected) {
		this.isCollected = isCollected;
	}

	public List<AdDetail> getAdDetails() {
		return adDetails;
	}

	public void setAdDetails(List<AdDetail> adDetails) {
		this.adDetails = adDetails;
	}

	public Boolean getIsAgreed() {
		return isAgreed;
	}

	public void setIsAgreed(Boolean isAgreed) {
		this.isAgreed = isAgreed;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public Integer getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
	}

	public List<AdDetail> getAdDetailList() {
		return adDetails;
	}

	public void setAdDetailList(List<AdDetail> adDetailList) {
		this.adDetails = adDetailList;
	}
	
}
