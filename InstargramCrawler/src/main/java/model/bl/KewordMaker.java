package model.bl;



import java.util.List;

import model.dao.KewordDAO;
import model.dto.KewordDTO;


/**
 * <h1>키워드 생성기</h1>
 * <pre>인스타그램에 검색할 키워드를 생성(디비 기반) </pre>
 * @author dongdang
 *
 */
public class KewordMaker {
	private KewordDAO kewordDAO;
	
	public KewordMaker() {
		// TODO Auto-generated constructor stub
		kewordDAO = new KewordDAO();
	}
	/**
	 * <h1> 키워드 생성 메소드 </h1>
	 * <pre> 안양시를 한정하여 디비에서 행정동명과 업소명을 가져와 키워드 생성 <pre>
	 * <ul>
	 *  <li>법정동</li>
	 *  <li>업소명</li>
	 *  <li>법정동 + 업소명</li>
	 * </ul>
	 * 
	 */

	public List<KewordDTO> make(List<String> ldongList) {
		List<KewordDTO> list = kewordDAO.getKeword(ldongList);
		for(KewordDTO dto : list) {
			dto.setLdongBizesNm(dto.getLdongNm() + " " + dto.getBlzesNm());
		}
		return list;
	}
	/** 
	 *  <h1> 법정동 리스트 메소드 </h1>
	 *  <pre> 해당지역의 법정동 리스트를 가져온다(분업을 위함)<pre>
	 */
	public List<String> getLdongList() {
		return kewordDAO.getLdongList();
	}

}
