package util.nsdi.dto;

public class AdmListResponseDTO {
	private AdmListDTO admVOList;
	public AdmListResponseDTO() {
		// TODO Auto-generated constructor stub
	}
	public AdmListResponseDTO(AdmListDTO admVOList) {
		super();
		this.admVOList = admVOList;
	}
	public AdmListDTO getAdmVOList() {
		return admVOList;
	}
	public void setAdmVOList(AdmListDTO admVOList) {
		this.admVOList = admVOList;
	}
	@Override
	public String toString() {
		return "CtprvnResponseDTO [admVOList=" + admVOList + "]";
	}
	
}
