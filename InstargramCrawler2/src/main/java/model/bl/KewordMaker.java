package model.bl;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import util.kakaoLocalSearch.KewordLocalSearch;
import util.kakaoLocalSearch.dto.LocalKeywordDocumentDTO;
import util.kakaoLocalSearch.dto.LocalKeywordRequestDTO;
import util.kakaoLocalSearch.dto.LocalKeywordResponseDTO;
import util.nsdi.AdongSearch;
import util.nsdi.CtprvnSearch;
import util.nsdi.SignguSearch;
import util.nsdi.dto.AdmDTO;

public class KewordMaker {
	
	public KewordMaker() {
		// TODO Auto-generated constructor stub
	}
	
	public LinkedList<String> dongMake(String ctprvn,String signgu) throws IOException {
		LinkedList<String> list = new LinkedList<String>();
		List<AdmDTO> admList = new CtprvnSearch().urlGetSend().getAdmVOList().getadmVOList();
		for(AdmDTO admDTO : admList) {
			if(admDTO.getAdmCodeNm().equals(ctprvn)) {
				List<AdmDTO> signguList = new SignguSearch().urlGetSend(admDTO.getAdmCode()).getAdmVOList().getadmVOList();
				for(AdmDTO signguDTO : signguList) {
					String temp = signguDTO.getLowestAdmCodeNm();
					if(temp.equals(signgu)) {
						List<AdmDTO> dongList = new AdongSearch().urlGetSend(signguDTO.getAdmCode()).getAdmVOList().getadmVOList();
						for(AdmDTO dong: dongList) {
							list.add(dong.getLowestAdmCodeNm());
							
						}
					}
				}
			}
		}
		return list;
	}
	
	public LinkedList<String> subwayMake(String signgu, List<String> dongList) throws IOException {
		//키워드 로컬서치
		HashSet<String> set = new HashSet<String>();
		KewordLocalSearch kewordLocalSearch = new KewordLocalSearch();
		for(String dong : dongList) {
			LocalKeywordRequestDTO requestDTO = new LocalKeywordRequestDTO(signgu + " " + dong);
			requestDTO.setCategory_group_code("SW8");//지하철
			LocalKeywordResponseDTO responseDTO = kewordLocalSearch.sendKewordQuery(requestDTO);
			for(LocalKeywordDocumentDTO document : responseDTO.getDocuments()) {
				set.add(document.getPlace_name().split(" ")[0]);
			}
		}
		return new LinkedList<String>(set);
	}
	
	public LinkedList<String> subwayMake(List<String> dongList) throws IOException {
		//키워드 로컬서치
		HashSet<String> set = new HashSet<String>();
		KewordLocalSearch kewordLocalSearch = new KewordLocalSearch();
		for(String dong : dongList) {
			LocalKeywordRequestDTO requestDTO = new LocalKeywordRequestDTO(dong);
			requestDTO.setCategory_group_code("SW8");//지하철
			LocalKeywordResponseDTO responseDTO = kewordLocalSearch.sendKewordQuery(requestDTO);
			for(LocalKeywordDocumentDTO document : responseDTO.getDocuments()) {
				set.add(document.getPlace_name().split(" ")[0]);
			}
		}
		return new LinkedList<String>(set);
	}
}
