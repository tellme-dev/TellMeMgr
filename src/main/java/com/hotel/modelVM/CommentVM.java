package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Comment;
import com.hotel.model.Reply;

public class CommentVM {
	private Comment comment;
	private List<Reply> replies;
	
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
}
