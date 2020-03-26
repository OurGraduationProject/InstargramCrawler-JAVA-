package util.nsdi.dto;

public class AdmDTO {
	String admCodeNm;
	int admCode;
	String lowestAdmCodeNm;
	String lnm;
	String mnnm;
	String slno;
	String ldCpsgCode;
	String ldEmdLiCode;
	String regstrSeCode;
	String pnu;
	
	public AdmDTO() {
		// TODO Auto-generated constructor stub
	}

	public AdmDTO(String admCodeNm, int admCode, String lowestAdmCodeNm, String lnm, String mnnm, String slno,
			String ldCpsgCode, String ldEmdLiCode, String regstrSeCode, String pnu) {
		super();
		this.admCodeNm = admCodeNm;
		this.admCode = admCode;
		this.lowestAdmCodeNm = lowestAdmCodeNm;
		this.lnm = lnm;
		this.mnnm = mnnm;
		this.slno = slno;
		this.ldCpsgCode = ldCpsgCode;
		this.ldEmdLiCode = ldEmdLiCode;
		this.regstrSeCode = regstrSeCode;
		this.pnu = pnu;
	}

	public String getAdmCodeNm() {
		return admCodeNm;
	}

	public void setAdmCodeNm(String admCodeNm) {
		this.admCodeNm = admCodeNm;
	}

	public int getAdmCode() {
		return admCode;
	}

	public void setAdmCode(int admCode) {
		this.admCode = admCode;
	}

	public String getLowestAdmCodeNm() {
		return lowestAdmCodeNm;
	}

	public void setLowestAdmCodeNm(String lowestAdmCodeNm) {
		this.lowestAdmCodeNm = lowestAdmCodeNm;
	}

	public String getLnm() {
		return lnm;
	}

	public void setLnm(String lnm) {
		this.lnm = lnm;
	}

	public String getMnnm() {
		return mnnm;
	}

	public void setMnnm(String mnnm) {
		this.mnnm = mnnm;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getLdCpsgCode() {
		return ldCpsgCode;
	}

	public void setLdCpsgCode(String ldCpsgCode) {
		this.ldCpsgCode = ldCpsgCode;
	}

	public String getLdEmdLiCode() {
		return ldEmdLiCode;
	}

	public void setLdEmdLiCode(String ldEmdLiCode) {
		this.ldEmdLiCode = ldEmdLiCode;
	}

	public String getRegstrSeCode() {
		return regstrSeCode;
	}

	public void setRegstrSeCode(String regstrSeCode) {
		this.regstrSeCode = regstrSeCode;
	}

	public String getPnu() {
		return pnu;
	}

	public void setPnu(String pnu) {
		this.pnu = pnu;
	}

	@Override
	public String toString() {
		return "CtprvnDTO [admCodeNm=" + admCodeNm + ", admCode=" + admCode + ", lowestAdmCodeNm=" + lowestAdmCodeNm
				+ ", lnm=" + lnm + ", mnnm=" + mnnm + ", slno=" + slno + ", ldCpsgCode=" + ldCpsgCode + ", ldEmdLiCode="
				+ ldEmdLiCode + ", regstrSeCode=" + regstrSeCode + ", pnu=" + pnu + "]";
	}
	
	
}
