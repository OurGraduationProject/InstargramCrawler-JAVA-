package model.bl;

import java.util.Iterator;
import java.util.LinkedList;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;


import model.dao.HashTagDAO;
import model.dto.HashTagDTO;
import model.exception.LoginException;

public class ThreadHashTagLinkScraper extends Thread{
	private WebDriver driver; //웹 드라이버(크롬)
	private InstargramScrapController controller;
	private InstargramScrap scrap;  //스크랩 기능을 가진 객체
	private HashTagDAO hashTagDAO;
	private int loginCode;
	public ThreadHashTagLinkScraper(String name, InstargramScrapController controller,WebDriver driver,int loginCode) {
		super(name);
		this.controller = controller;	
		this.driver = driver;
		this.loginCode = loginCode;
		scrapSetting();
		daoSetting();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		super.run();
		/*
		 * 순서
		 * 1. 로그인
		 * 2. 검색 리스트 수집
		 * 3. 게시물링크 수집
		 */
		try {
			new InstargramLogin().login(driver,loginCode);
		}catch(LoginException e) {
			errMessage(e.getMessage());
		}
//검색리스트 수집
		
		String keword = null;
		while((keword = controller.getKeword())!=null) {
			printMessage(keword+"검색리스트를 수집합니다.");
			searchListScrap(keword);
			printMessage(keword+"검색리스트를 완성했습니다.");	
		}
		
	}
	
	/**
	 * <pre>url요청 동기화</pre>
	 * 
	 */
	public void getUrl(String url) {
		controller.getUrl(driver, url);
	}
	
	/**
	 * <pre>스크랩 객체에 검색리스트를 수집하도록 요청</pre>
	 */
	private void searchListScrap(String hashTag) {
		LinkedList<HashTagDTO> list = null;
		try {
			list = this.scrap.searchListScrap(hashTag,this.driver);
			Iterator<HashTagDTO> iter = list.iterator();
			try {
				
				while(iter.hasNext()) {
					HashTagDTO dto = iter.next();

					if(!checkHashTag(dto.getHashTagAdr())) {
						printMessage(dto.getHashTagNm() + "은 해시태그 테이블에 존재하기 때문에 검색리스트에서 제외합니다.");
						iter.remove();
					}else {
						printMessage(dto.getHashTagNm() + "를 검색리스트에 추가합니다.");
					}
				}
				printMessage(list.toString());
				controller.addHashTagAll(list);
				printMessage(hashTag+" 검색링크 수집 완료하였습니다.");
			}catch(NoSuchElementException e) {
				errMessage(hashTag+"수집을 할 수 없어서, 다음 수집으로 진행하겠습니다.");
			}
		}catch(Exception e) {
			this.errMessage(hashTag+"에서 error가 발생하였습니다.");
		}
		
	}

////////////////////////////////////////////////////////////////////////////////////////
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
