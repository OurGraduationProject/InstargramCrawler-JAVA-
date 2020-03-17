package model.dto;

import java.sql.Date;

public class CommentDTO {
	private String id;
	private String content;
	private Date creationDate;
	private String contentAdr;
	
	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public CommentDTO(String id, String content, Date creationDate, String contentAdr) {
		super();
		this.id = id;
		this.content = content;
		this.creationDate = creationDate;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getContentAdr() {
		return contentAdr;
	}

	public void setContentAdr(String contentAdr) {
		this.contentAdr = contentAdr;
	}

	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", content=" + content + ", creationDate=" + creationDate + ", contentAdr="
				+ contentAdr + "]";
	}
	
}
