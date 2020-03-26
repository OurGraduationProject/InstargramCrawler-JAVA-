package util.kakaoLocalSearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import util.kakaoLocalSearch.dto.LocalKeywordRequestDTO;
import util.kakaoLocalSearch.dto.LocalKeywordResponseDTO;




public class KewordLocalSearch extends KakaoAPIService{
	
	public KewordLocalSearch() throws FileNotFoundException, IOException {
		super();
	}
	/** 키워드를 통한 지역 검색 */
	public LocalKeywordResponseDTO sendKewordQuery(LocalKeywordRequestDTO requestDTO) throws IOException  {
		/*
		 * 1. KakaoAPIService에서 kewordURL 가져오기
		 * 2. URL 세팅
		 * 3. urlGetSend 메소드에 파라미터로 전달.
		 */
		//1
		String url = getURL("localKewordSearchUrl");
		//2
		url += "query="+URLEncoder.encode(requestDTO.getQuery(), "UTF-8")+requestDTO.toString();
		//3
		return (LocalKeywordResponseDTO) urlGetSend(url, LocalKeywordResponseDTO.class);	
	}

}
