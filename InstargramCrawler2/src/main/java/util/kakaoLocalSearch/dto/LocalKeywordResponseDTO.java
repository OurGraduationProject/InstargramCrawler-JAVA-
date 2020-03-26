package util.kakaoLocalSearch.dto;

import java.util.List;
/**
 * 카카오 API - 키워드를 이용한 지역검색 응답 DTO 
 * @author dongdang
 *
 */
public class LocalKeywordResponseDTO {
	/*
	 * meta = 응답정보
	 * documents = 장소 정보들이 담긴 리스트
	 */
	private LocalKeywordMetaDTO meta;
	private List<LocalKeywordDocumentDTO> documents;
	
	/** 기본 생성자 */
	public LocalKeywordResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/** 모든 멤버 변수 초기화  */
	public LocalKeywordResponseDTO(LocalKeywordMetaDTO meta, List<LocalKeywordDocumentDTO> documents) {
		super();
		this.meta = meta;
		this.documents = documents;
	}
	//getters, setters
	/**
	 * @return the meta
	 */
	public LocalKeywordMetaDTO getMeta() {
		return meta;
	}
	/**
	 * @param meta the meta to set
	 */
	public void setMeta(LocalKeywordMetaDTO meta) {
		this.meta = meta;
	}
	/**
	 * @return the documents
	 */
	public List<LocalKeywordDocumentDTO> getDocuments() {
		return documents;
	}
	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<LocalKeywordDocumentDTO> documents) {
		this.documents = documents;
	}
	@Override
	public String toString() {
		return "LocalKewordResponseDTO [meta=" + meta + ", documents=" + documents + "]";
	}
		
}
