package util.kakaoLocalSearch.dto;
/**
 * <strong>LocalKeywordResponseDTO에 포함되는 클래스</strong>
 * <pre>응답받은 문서의 정보가 담김</pre>
 * @author dongdang
 *
 */
public class LocalKeywordMetaDTO {
	/*
	 * total_count = 검색어에 검색된 문서수
	 * pageable_count = total_count 중에 노출 가능 문서수
	 * is_end = 현재 페이지가 마지막 페이지인지 여부. false -> 없음, true -> 있음
	 * same_name = 질의어의 지역, 키워드 분석 정보 
	 */
	
	private Integer total_count;
	private Integer pageable_count;
	private boolean is_end;
	private LocalKeywordMetaSameNameDTO same_name;
	
	/** 기본 생성자 */
	public LocalKeywordMetaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/** 모든 멤버 변수 초기화 */
	public LocalKeywordMetaDTO(Integer total_count, Integer pageable_count, boolean is_end,
			LocalKeywordMetaSameNameDTO same_name) {
		super();
		this.total_count = total_count;
		this.pageable_count = pageable_count;
		this.is_end = is_end;
		this.same_name = same_name;
	}

	//getters, setters
	/**
	 * @return the total_count
	 */
	public Integer getTotal_count() {
		return total_count;
	}

	/**
	 * @param total_count the total_count to set
	 */
	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	/**
	 * @return the pageable_count
	 */
	public Integer getPageable_count() {
		return pageable_count;
	}

	/**
	 * @param pageable_count the pageable_count to set
	 */
	public void setPageable_count(Integer pageable_count) {
		this.pageable_count = pageable_count;
	}

	/**
	 * @return the is_end
	 */
	public boolean isIs_end() {
		return is_end;
	}

	/**
	 * @param is_end the is_end to set
	 */
	public void setIs_end(boolean is_end) {
		this.is_end = is_end;
	}

	/**
	 * @return the same_name
	 */
	public LocalKeywordMetaSameNameDTO getSame_name() {
		return same_name;
	}

	/**
	 * @param same_name the same_name to set
	 */
	public void setSame_name(LocalKeywordMetaSameNameDTO same_name) {
		this.same_name = same_name;
	}

	//toString
	@Override
	public String toString() {
		return "LocalKewordMetaDTO [total_count=" + total_count + ", pageable_count=" + pageable_count + ", is_end="
				+ is_end + ", same_name=" + same_name + "]";
	}
	

}
