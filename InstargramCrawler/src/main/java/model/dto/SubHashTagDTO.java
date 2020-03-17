package model.dto;

public class SubHashTagDTO {
	private String contentAdr;
	private String hashTagNm;
	
	public SubHashTagDTO() {
		// TODO Auto-generated constructor stub
	}

	public SubHashTagDTO(String contentAdr, String hashTagNm) {
		super();
		this.contentAdr = contentAdr;
		this.hashTagNm = hashTagNm;
	}

	public String getContentAdr() {
		return contentAdr;
	}

	public void setContentAdr(String contentAdr) {
		this.contentAdr = contentAdr;
	}

	public String getHashTagNm() {
		return hashTagNm;
	}

	public void setHashTagNm(String hashTagNm) {
		this.hashTagNm = hashTagNm;
	}

	@Override
	public String toString() {
		return "SubHashTagDTO [contentAdr=" + contentAdr + ", hashTagNm=" + hashTagNm + "]";
	}
	
	
}
