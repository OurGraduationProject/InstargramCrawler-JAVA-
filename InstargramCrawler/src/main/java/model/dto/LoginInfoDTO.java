package model.dto;

public class LoginInfoDTO {
	private String id;
	private String pwd;
	
	public LoginInfoDTO() {
		// TODO Auto-generated constructor stub
	}

	public LoginInfoDTO(String id, String pwd) {
		super();
		this.id = id;
		this.pwd = pwd;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "LoginInfoDTO [id=" + id + ", pwd=" + pwd + "]";
	}
	
	
}