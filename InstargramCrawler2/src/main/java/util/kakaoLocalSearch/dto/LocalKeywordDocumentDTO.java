package util.kakaoLocalSearch.dto;

/**
 * <strong>LocalKeywordReponseDTO에 포함되는 클래스</strong>
 * <pre>장소의 정보를 담음</pre>
 * @author dongdang
 *
 */
public class LocalKeywordDocumentDTO {
	/*
	 * id = 장소 ID
	 * place_name=장소명, 업체명
	 * category_name=카테고리 이름
	 * category_group_code=중요 카테고리만 그룹핑한 카테고리 그룹 코드. Request에 category_group_code 테이블 참고
	 * category_group_name=중요 카테고리만 그룹핑한 카테고리 그룹명.
	 * Request에 category_group_code 테이블 참고
	 * phone=전화번호
	 * address_name=전체 지번 주소
	 * road_address_name=전체 도로명 주소
	 * x=X 좌표값 혹은 longitude
	 * y=Y 좌표값 혹은 latitude
	 * place_url=장소 상세페이지 URL
	 * distance=중심좌표까지의 거리(x,y 파라미터를 준 경우에만 존재). 단위 meter
	 */
	private String id;
	private String place_name;
	private String category_name;
	private String category_group_code;
	private String category_group_name;
	private String phone;
	private String address_name;
	private String road_address_name;
	private String x,y;
	private String place_url;
	private String distance;
	
	/** 기본 생성자 */
	public LocalKeywordDocumentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** 모든 변수 초기화 */
	public LocalKeywordDocumentDTO(String id, String place_name, String category_name, String category_group_code,
			String category_group_name, String phone, String address_name, String road_address_name, String x, String y,
			String place_url, String distance) {
		super();
		this.id = id;
		this.place_name = place_name;
		this.category_name = category_name;
		this.category_group_code = category_group_code;
		this.category_group_name = category_group_name;
		this.phone = phone;
		this.address_name = address_name;
		this.road_address_name = road_address_name;
		this.x = x;
		this.y = y;
		this.place_url = place_url;
		this.distance = distance;
	}

	//getters, setters
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
	 * @return the place_name
	 */
	public String getPlace_name() {
		return place_name;
	}


	/**
	 * @param place_name the place_name to set
	 */
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}


	/**
	 * @return the category_name
	 */
	public String getCategory_name() {
		return category_name;
	}


	/**
	 * @param category_name the category_name to set
	 */
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}


	/**
	 * @return the category_group_code
	 */
	public String getCategory_group_code() {
		return category_group_code;
	}


	/**
	 * @param category_group_code the category_group_code to set
	 */
	public void setCategory_group_code(String category_group_code) {
		this.category_group_code = category_group_code;
	}


	/**
	 * @return the category_group_name
	 */
	public String getCategory_group_name() {
		return category_group_name;
	}


	/**
	 * @param category_group_name the category_group_name to set
	 */
	public void setCategory_group_name(String category_group_name) {
		this.category_group_name = category_group_name;
	}


	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}


	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}


	/**
	 * @return the address_name
	 */
	public String getAddress_name() {
		return address_name;
	}


	/**
	 * @param address_name the address_name to set
	 */
	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}


	/**
	 * @return the road_address_name
	 */
	public String getRoad_address_name() {
		return road_address_name;
	}


	/**
	 * @param road_address_name the road_address_name to set
	 */
	public void setRoad_address_name(String road_address_name) {
		this.road_address_name = road_address_name;
	}


	/**
	 * @return the x
	 */
	public String getX() {
		return x;
	}


	/**
	 * @param x the x to set
	 */
	public void setX(String x) {
		this.x = x;
	}


	/**
	 * @return the y
	 */
	public String getY() {
		return y;
	}


	/**
	 * @param y the y to set
	 */
	public void setY(String y) {
		this.y = y;
	}


	/**
	 * @return the place_url
	 */
	public String getPlace_url() {
		return place_url;
	}


	/**
	 * @param place_url the place_url to set
	 */
	public void setPlace_url(String place_url) {
		this.place_url = place_url;
	}


	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}


	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	//toString
	@Override
	public String toString() {
		return "LocalKewordDocumentDTO [id=" + id + ", place_name=" + place_name + ", category_name=" + category_name
				+ ", category_group_code=" + category_group_code + ", category_group_name=" + category_group_name
				+ ", phone=" + phone + ", address_name=" + address_name + ", road_address_name=" + road_address_name
				+ ", x=" + x + ", y=" + y + ", place_url=" + place_url + ", distance=" + distance + "]";
	}
	
	
	
}
