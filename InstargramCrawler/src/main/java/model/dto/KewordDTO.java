package model.dto;

public class KewordDTO {
	private String bizesId;
	private String ldongNm;
	private String blzesNm;
	private String ldongBizesNm;
	public KewordDTO() {
		// TODO Auto-generated constructor stub
	}
	public KewordDTO(String bizesId, String ldongNm, String blzesNm) {
		super();
		this.bizesId = bizesId;
		this.ldongNm = ldongNm;
		this.blzesNm = blzesNm;
	}
	public KewordDTO(String bizesId, String ldongNm, String blzesNm, String ldongBizesNm) {
		super();
		this.bizesId = bizesId;
		this.ldongNm = ldongNm;
		this.blzesNm = blzesNm;
		this.ldongBizesNm = ldongBizesNm;
	}
	public String getBizesId() {
		return bizesId;
	}
	public void setBizesId(String bizesId) {
		this.bizesId = bizesId;
	}
	public String getLdongNm() {
		return ldongNm;
	}
	public void setLdongNm(String ldongNm) {
		this.ldongNm = ldongNm;
	}
	public String getBlzesNm() {
		return blzesNm;
	}
	public void setBlzesNm(String blzesNm) {
		this.blzesNm = blzesNm;
	}
	public String getLdongBizesNm() {
		return ldongBizesNm;
	}
	public void setLdongBizesNm(String ldongBizesNm) {
		this.ldongBizesNm = ldongBizesNm;
	}
	@Override
	public String toString() {
		return "KewordDTO [bizesId=" + bizesId + ", ldongNm=" + ldongNm + ", blzesNm=" + blzesNm + ", ldongBizesNm="
				+ ldongBizesNm + "]";
	}
	
	
}