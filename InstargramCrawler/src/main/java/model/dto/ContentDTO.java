package model.dto;

import java.sql.Date;
import java.util.List;

public class ContentDTO {
	private String contentAdr;
	private String id;
	private String content;
	private int good;
	private int commentNum;
	private Date creationDate;
	private List<SubHashTagDTO> subHashTagList;
	private List<CommentDTO> commentList;
	public ContentDTO() {
		// TODO Auto-generated constructor stub
	}
	public ContentDTO(String contentAdr, String id, String content, int good, int commentNum, Date creationDate,
			List<SubHashTagDTO> subHashTagList, List<CommentDTO> commentList) {
		super();
		this.contentAdr = contentAdr;
		this.id = id;
		this.content = content;
		this.good = good;
		this.commentNum = commentNum;
		this.creationDate = creationDate;
		this.subHashTagList = subHashTagList;
		this.commentList = commentList;
	}
	public String getContentAdr() {
		return contentAdr;
	}
	public void setContentAdr(String contentAdr) {
		this.contentAdr = contentAdr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public List<SubHashTagDTO> getSubHashTagList() {
		return subHashTagList;
	}
	public void setSubHashTagList(List<SubHashTagDTO> subHashTagList) {
		this.subHashTagList = subHashTagList;
	}
	public List<CommentDTO> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<CommentDTO> commentList) {
		this.commentList = commentList;
	}
	@Override
	public String toString() {
		return "ContentDTO [contentAdr=" + contentAdr + ", id=" + id + ", content=" + content + ", good=" + good
				+ ", commentNum=" + commentNum + ", creationDate=" + creationDate + ", subHashTagList=" + subHashTagList
				+ ", commentList=" + commentList + "]";
	}
	
}
