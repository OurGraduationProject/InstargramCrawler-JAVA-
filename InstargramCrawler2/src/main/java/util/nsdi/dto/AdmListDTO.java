package util.nsdi.dto;

import java.util.List;

public class AdmListDTO {
	List<AdmDTO> admVOList;
	int totalCount;
	int numOfRows;
	int pageNo;
	String error;
	String message;
	public AdmListDTO() {
		// TODO Auto-generated constructor stub
	}
	public AdmListDTO(List<AdmDTO> admVOList, int totalCount, int numOfRows, int pageNo, String error,
			String message) {
		super();
		this.admVOList = admVOList;
		this.totalCount = totalCount;
		this.numOfRows = numOfRows;
		this.pageNo = pageNo;
		this.error = error;
		this.message = message;
	}
	public List<AdmDTO> getadmVOList() {
		return admVOList;
	}
	public void setadmVOList(List<AdmDTO> admVOList) {
		this.admVOList = admVOList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getNumOfRows() {
		return numOfRows;
	}
	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "CtprvnDTOList [admVOList=" + admVOList + ", totalCount=" + totalCount + ", numOfRows=" + numOfRows
				+ ", pageNo=" + pageNo + ", error=" + error + ", message=" + message + "]";
	}
		
}
