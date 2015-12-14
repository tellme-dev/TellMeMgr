package com.hotel.modelVM;

import java.util.List;

public class BbsCommentVM {
	private BbsVM post;
	private List<BbsDynamicVM> dynamics;
	
	public BbsVM getPost() {
		return post;
	}
	public void setPost(BbsVM post) {
		this.post = post;
	}
	public List<BbsDynamicVM> getDynamics() {
		return dynamics;
	}
	public void setDynamics(List<BbsDynamicVM> dynamics) {
		this.dynamics = dynamics;
	}
}
