package model.bl;

import org.openqa.selenium.WebDriver;

import model.dao.HashTagDAO;
import model.dto.CommentDTO;
import model.dto.ContentDTO;
import model.dto.HashTagDTO;
import model.dto.SubHashTagDTO;

public class ThreadHashTagScraper extends Thread{
	private WebDriver driver; //웹 드라이버(크롬)
	private InstargramScrapController controller;
	private InstargramScrap scrap;  //스크랩 기능을 가진 객체
	private HashTagDAO hashTagDAO;
	public ThreadHashTagScraper(String name, InstargramScrapController controller,WebDriver driver) {
		super(name);
		this.controller = controller;
		this.driver = driver;
		scrapSetting();
		daoSetting();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		HashTagDTO hashTag = null;
		while((hashTag= controller.getHashTag()) !=null) {
			String nm = hashTag.getHashTagNm();
			printMessage(nm+"작업을 시작합니다.");
///////////////////////////////////////////////////////////
//게시물링크 수집		
			printMessage(nm+"게시물링크를 수집합니다.");
			contentListScrap(hashTag);
			printMessage(nm+"게시물링크를 수집 완료했습니다.");
		
///////////////////////////////////////////////////////////
//게시물정보 수집				
			printMessage(nm+"게시물 정보를 수집합니다.");
			contentScrap(hashTag);
			printMessage(nm+"게시물 정보를 수집 완료했습니다.");
		
		
///////////////////////////////////////////////////////////				
//DB 저장			
			printMessage(nm+"를 DB에 저장합니다.");
			insertHashTag(hashTag);
			printMessage(nm+"를 DB에 저장했습니다.");
		}
	}
	
	/**
	 * <pre>스크랩 객체에 게시물 링크를 수집하도록 일괄 요청</pre>
	 */
	private void contentListScrap(HashTagDTO hashTagDTO) {
		
		this.scrap.contentLinkScrap(hashTagDTO, driver);
		
	}
	
	/**
	 * <pre>스크랩 객체에 게시글을 수집하도록 일괄 요청</pre>
	 */
	private void contentScrap(HashTagDTO hashTagDTO) {
		
		this.scrap.contentScrap(hashTagDTO, driver);
		
	}
	/**
	 * <pre>수집한 HashTag 정보들을 DB에 저장하도록 DAO에 테이블마다 알맞게 요청</pre>
	 * 
	 */
	private void insertHashTag(HashTagDTO hashTag) {
		
		printMessage("해시태그 : "+hashTag.getHashTagNm()+" DB 작업중...");
		for(String key : hashTag.getContentMap().keySet()) {
			ContentDTO content = hashTag.getContentMap().get(key);
			printMessage("게시글 : "+hashTag.getHashTagNm()+"->"+content.getContentAdr()+" DB 작업중...");
			hashTagDAO.insertContent(content);
			hashTagDAO.insertConContent(hashTag.getHashTagAdr(),content.getContentAdr());
			
			for(CommentDTO comment : content.getCommentList()) {
				printMessage("댓글 : "+hashTag.getHashTagNm()+"->"+content.getContentAdr()+"->"+ comment.getId()+" DB 작업중...");
				hashTagDAO.insertComment(comment);
			}
			for(SubHashTagDTO subHashTagDTO : content.getSubHashTagList()) {
				printMessage("서브해시태그 : "+hashTag.getHashTagNm()+"->"+content.getContentAdr()+"->"+ subHashTagDTO.getHashTagNm()+" DB 작업중...");
				hashTagDAO.insertSubHash(subHashTagDTO);
			}
		}
		hashTagDAO.insertHashTag(hashTag);
	}
	
	/**
	 * <pre>url요청 동기화</pre>
	 * 
	 */
	public void getUrl(String url) {
		controller.getUrl(driver, url);
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
//환경 설정
	/**
	 * <pre> 스크랩에 필요한 객체 정의 </pre>
	 */
	private void scrapSetting() {
		this.scrap = new InstargramScrap(this);
	}	
	
	/**
	 * <pre> DB 접근 객체 환경 세팅 </pre>
	 */
	private void daoSetting() {
		this.hashTagDAO = new HashTagDAO();
	}

//출력 형식
	/**
	 * 
	 *<pre> 스레드 이름을 담은 평시 메시지 출력 </pre>
	 * @param message
	 */
	public void printMessage(String message) {
		System.out.println("------->["+this.getName()+"] "+ message);
	}	
	
	
	/**
	 * 
	 *<pre> 스레드 이름을 담은 에러 메시지 출력 </pre>
	 * @param message
	 */
	public void errMessage(String message) {
		System.err.println("------->["+this.getName()+"] "+ message);
	}
}
