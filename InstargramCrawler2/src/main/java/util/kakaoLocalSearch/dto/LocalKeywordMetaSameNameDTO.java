package util.kakaoLocalSearch.dto;

import java.util.List;

/**
 * <strong>LocalKeywordMetaDTO에 포함되는 클래스</strong>
 * <pre>질의어에 인식한 결과값과 비슷한 정보들이 담김</pre>
 * @author dongdang
 */
public class LocalKeywordMetaSameNameDTO {
	/*
	 * region = 질의어에서 인식된 지역의 리스트
	 * keyword = 질의어에서 지역 정보를 제외한 키워드
	 * selected_region =  인식된 지역 리스트 중, 현재 검색에 사용된 지역정보
	 */
	private List<String> region;
	private String keyword;
	private String selected_region;
	/** 모든 변수 초기화 **/
	public LocalKeywordMetaSameNameDTO(List<String> region, String keyword, String selected_region) {
		super();
		this.region = region;
		this.keyword = keyword;
		this.selected_region = selected_region;
	}

	//getters, setters
	/**
	 * @return the region
	 */
	public List<String> getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(List<String> region) {
		this.region = region;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the selected_region
	 */
	public String getSelected_region() {
		return selected_region;
	}

	/**
	 * @param selected_region the selected_region to set
	 */
	public void setSelected_region(String selected_region) {
		this.selected_region = selected_region;
	}

	//toString
	@Override
	public String toString() {
		return "LocalKewordMetaSameNameDTO [region=" + region + ", keyword=" + keyword + ", selected_region="
				+ selected_region + "]";
	}
	
}
