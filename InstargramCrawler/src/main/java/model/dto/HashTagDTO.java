package model.dto;

import java.util.HashMap;

public class HashTagDTO {
	private String bizesId;
	private String hashTagNm;
	private String hashTagAdr;
	private int contentNum;
	private HashMap<String,ContentDTO> contentMap;
	
	public HashTagDTO() {
		// TODO Auto-generated constructor stub
	}

	public HashTagDTO(String bizesId, String hashTagNm, String hashTagAdr, int contentNum,
			HashMap<String, ContentDTO> contentMap) {
		super();
		this.bizesId = bizesId;
		this.hashTagNm = hashTagNm;
		this.hashTagAdr = hashTagAdr;
		this.contentNum = contentNum;
		this.contentMap = contentMap;
	}

	public String getBizesId() {
		return bizesId;
	}

	public void setBizesId(String bizesId) {
		this.bizesId = bizesId;
	}

	public String getHashTagNm() {
		return hashTagNm;
	}

	public void setHashTagNm(String hashTagNm) {
		this.hashTagNm = hashTagNm;
	}

	public String getHashTagAdr() {
		return hashTagAdr;
	}

	public void setHashTagAdr(String hashTagAdr) {
		this.hashTagAdr = hashTagAdr;
	}

	public int getContentNum() {
		return contentNum;
	}

	public void setContentNum(int contentNum) {
		this.contentNum = contentNum;
	}

	public HashMap<String, ContentDTO> getContentMap() {
		return contentMap;
	}

	public void setContentMap(HashMap<String, ContentDTO> contentMap) {
		this.contentMap = contentMap;
	}

	@Override
	public String toString() {
		return "HashTagDTO [bizesId=" + bizesId + ", hashTagNm=" + hashTagNm + ", hashTagAdr=" + hashTagAdr
				+ ", contentNum=" + contentNum + ", contentMap=" + contentMap + "]";
	}
	
	
}
