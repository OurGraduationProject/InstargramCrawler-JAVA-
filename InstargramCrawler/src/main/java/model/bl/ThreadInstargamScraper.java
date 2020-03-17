package model.bl;


import java.util.Iterator;
import java.util.LinkedList;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import model.dao.HashTagDAO;
import model.dto.CommentDTO;
import model.dto.ContentDTO;
import model.dto.HashTagDTO;
import model.dto.KewordDTO;
import model.dto.SubHashTagDTO;
import model.exception.LoginException;
import util.MetaDataLoader;

public class ThreadInstargamScraper extends Thread {
	
	private WebDriver driver; //웹 드라이버(크롬)
	private InstargramScrapController controller; //자신을 제어할 컨트롤러
	private InstargramScrap scrap;  //스크랩 기능을 가진 객체
	private InstargramLogin login; //인스타그램 로그인 기능 제공
	private LinkedList<HashTagDTO> searchListURL; //HashTag 수집 정보로 이루어진 리스트
	private HashTagDAO hashTagDAO; //해시태그 DB 접근 객체
	
	/**
	 * <pre>생성자</pre>
	 * @param threadName
	 * @param controller
	 */
	public ThreadInstargamScraper(String threadName, InstargramScrapController controller) {
		super(threadName);
		// TODO Auto-generated constructor stub
		this.controller = controller;
		driverSetting();	
		scrapSetting();
		daoSetting();
	}

	/**
	 * <pre>스레드 동작을 위한 메소드</pre>
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		instargramScrap();
	}
	/**
	 * <pre>인스타그램 수집에 대해 전체로직.</pre>
	 */
	public boolean instargramScrap() {

		KewordDTO keword = null;
		
			
		try {
			/*
			 * 순서
			 * 1. 로그인
			 * 2. 키워드 생성하기.
			 * 3. 검색 리스트 수집
			 * 4. 게시물링크 수집
			 * 5. 게시물 내용 수집.
			 * 6. DB에 저장.
			 * 2~6 반복
			 */
			try {
				login = new InstargramLogin();
				login.login(driver);
			}catch(LoginException e) {
				errMessage(e.getMessage());
			}
			
			while((keword = controller.getKeword())!=null) {
				
				printMessage(keword+"작업을 시작합니다.");
//////////////////////////////////////////////////////////
//검색리스트 수집				
				printMessage(keword+"검색리스트를 수집합니다.");
				
				searchListScrap(keword.getBizesId(), keword.getLdongNm());
				searchListScrap(keword.getBizesId(), keword.getBlzesNm());
				searchListScrap(keword.getBizesId(), keword.getLdongBizesNm());
				printMessage(keword+"검색리스트를 완성했습니다.");
				
				
///////////////////////////////////////////////////////////
//게시물링크 수집		
				printMessage(keword+"게시물링크를 수집합니다.");
				contentListScrap();
				printMessage(keword+"게시물링크를 수집 완료했습니다.");

///////////////////////////////////////////////////////////
//게시물정보 수집				
				printMessage(keword+"게시물 정보를 수집합니다.");
				contentScrap();
				printMessage(keword+"게시물 정보를 수집 완료했습니다.");
				
				
///////////////////////////////////////////////////////////				
//DB 저장			
				printMessage(keword+"를 DB에 저장합니다.");
				insertHashTag();
				printMessage(keword+"를 DB에 저장했습니다.");
///////////////////////////////////////////////////////////
//List정보 삭제
				searchListURL.clear();
///////////////////////////////////////////////////////////
				
				if(this.getName().equals("Scraper 0")) {
					printMessage("초기세팅 스크래퍼를 종료하고, 스레드 작업을 진행합니다.");
					break;
				}
				
			}
		}catch(Exception e) {
			errMessage("수집 중에 알 수 없는 에러가 발생하였습니다.");
			e.printStackTrace();
			
		}finally {
			driver.close();
			driver.quit();
		}
		return true;
	}


	/**
	 * <pre>
	 * 테이블에 해시태그가 없으면 true, 있으면 false
	 * 있다는 것은 이미 크롤링을 한 것을 의미함.
	 * </pre>
	 * @param hashTag
	 * @return
	 */
	private boolean checkHashTag(String hashTag) {
		if(hashTagDAO.checkHashTag(hashTag)== null) return true;
		return false;
	}

	/** <pre>
	* 연결테이블에 상권명 -해시태그가 없으면  true, 있으면 false
	* 해시태그에는 존재하지만, 연결테이블에 없는 이유는 비정상적 종료, 새로운 상권명, 동일한 해시태그를 의미함.
	* 따라서 연결테이블에 추가해주는게 시스템 안정화에 도움됨.
	* </pre>
	 */
	private boolean checkConHashTag(String bizesId, String hashTag) {
		if(hashTagDAO.checkConHashTag(bizesId,hashTag)== null) return true;
		return false;
	}

	/**
	 *
	 * <pre>이미 수집된 해시태그와 상가ID를 연결하도록 DAO에 요청</pre>
	 */
	private int insertConHashTag(String bizesId, String hashTag) {
		 return hashTagDAO.insertConHashTag(bizesId, hashTag);
	 }

	/**
	 * <pre>수집한 HashTag 정보들을 DB에 저장하도록 DAO에 테이블마다 알맞게 요청</pre>
	 * 
	 */
	private void insertHashTag() {
		for(HashTagDTO hashTag : searchListURL) {
			printMessage("해시태그 : "+hashTag.getHashTagNm()+" DB 작업중...");
			hashTagDAO.insertHashTag(hashTag);
			
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
			hashTagDAO.insertConHashTag(hashTag.getBizesId(),hashTag.getHashTagAdr());
		}
		
	}

	/**
	 * <pre>스크랩 객체에 검색리스트를 수집하도록 요청</pre>
	 */
	private void searchListScrap(String bizesId, String hashTag) {
		LinkedList<HashTagDTO> list = null;
		try {
			list = this.scrap.searchListScrap(hashTag,this.driver);
			Iterator<HashTagDTO> iter = list.iterator();
			try {
				
				while(iter.hasNext()) {
					HashTagDTO dto = iter.next();
					printMessage(dto.toString());
					if(!checkHashTag(dto.getHashTagAdr())) {
						printMessage(dto.getHashTagNm() + "은 해시태그 테이블에 존재합니다.");
						printMessage("문제 없는 데이터 처리를 위해 bizes-hashTag 연결테이블을 확인합니다.");
						if(checkConHashTag(bizesId,dto.getHashTagAdr())) {
							printMessage("연결테이블에 존재하지 않아 "+dto.getHashTagNm()+"를 삽입합니다.");
							insertConHashTag(bizesId,dto.getHashTagAdr());						
						}else {
							printMessage("확인되었습니다.");
						}
						iter.remove();
					}else {
						printMessage(dto.getHashTagNm() + "를 검색리스트에 추가합니다.");
						dto.setBizesId(bizesId);
					}
				}
				searchListURL.addAll(list);
				printMessage(hashTag+" 검색링크 수집 완료하였습니다.");
			}catch(NoSuchElementException e) {
				errMessage(hashTag+"수집을 할 수 없어서, 다음 수집으로 진행하겠습니다.");
			}
		}catch(NoSuchElementException e) {
			this.errMessage(hashTag+"에서 error가 발생하였습니다.");
			login.errorHandling(driver);
		}
		
	}
	
	/**
	 * <pre>스크랩 객체에 게시물 링크를 수집하도록 일괄 요청</pre>
	 */
	private void contentListScrap() {
		for(HashTagDTO hashTagDTO : searchListURL) {
			this.scrap.contentLinkScrap(hashTagDTO, driver);
		}

		
	}
	
	/**
	 * <pre>스크랩 객체에 게시글을 수집하도록 일괄 요청</pre>
	 */
	private void contentScrap() {
		for(HashTagDTO hashTagDTO : searchListURL) {
			this.scrap.contentScrap(hashTagDTO, driver);
		}
	}
	

//환경 설정
	/**
	 * <pre> 크롬 드라이버 세팅 </pre>
	 */
	private void driverSetting() {
		String webDriverID = MetaDataLoader.getSeleniumProfile().getProperty("WEB_DRIVER_ID");
		String webDriverPath = MetaDataLoader.getSeleniumProfile().getProperty("WEB_DRIVER_PATH");
		System.setProperty(webDriverID,webDriverPath);
		this.driver = new ChromeDriver();
		this.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		driver.manage().deleteAllCookies(); 
	}
	/**
	 * <pre> 스크랩에 필요한 객체 정의 </pre>
	 */
	private void scrapSetting() {
		this.scrap = new InstargramScrap(this);
		this.searchListURL = new LinkedList<HashTagDTO>();
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
