package model.bl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import model.dto.HashTagDTO;
import util.MetaDataLoader;


public class InstargramScrapController {
	//로그인 코드
	private int loginCode;
	//스레드 개수
	private int linkThreadNum;
	//키워드 리스트
	private LinkedList<String> kewordList;
	//해시태그 리스트
	private LinkedList<HashTagDTO> hashTagList;
	//driver 제어 리스트
	private ArrayList<WebDriver> driverList;
	//스레드 리스트
	private List<ThreadHashTagLinkScraper> linkScraperList;
	private List<ThreadHashTagScraper> tagScraperList;
	
	public static final String seedURL = "https://www.instagram.com";
	//인스타그램 날짜 포맷
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	// 스크롤 반복 횟수 제한(10번 반복동안 데이터 수 변환 없으면 그만 내림)
	public static final int REPEAT_LIMIT = 10; 
	//기본 댓글 출력 개수
	public static final int COMMENT_UNIT = 12;
	//검색리스트 갯수 제한 
	public static final int hashSearchCount = -1;
	//스레드 생성 지연 시간
	public static final int THREAD_DELAY_TIME = 1000;
	
	/**
	 * 생성자 - 키워드 리스트 생성
	 * @param threadNum
	 * @param dongList
	 */
	
	public InstargramScrapController(int threadNum, LinkedList<String> list, int loginCode) {
		// TODO Auto-generated constructor stub
		this.linkThreadNum = threadNum;
		this.kewordList = list;
		this.loginCode = loginCode;
		this.hashTagList = new LinkedList<HashTagDTO>();
		this.driverList = new ArrayList<WebDriver>();
		
	}
	/**
	 * <pre> 스크랩 시작 </pre>
	 */
	public void startScrap() {
		this.linkScraperList = new ArrayList<ThreadHashTagLinkScraper>();
		this.tagScraperList = new ArrayList<ThreadHashTagScraper>();
		
//링크,태그 스레드 생성
				
		try {
			for(int i=1; i<=linkThreadNum; i++) {
				WebDriver driver= driverSetting();
				driverList.add(driver);
				linkScraperList.add(this.createLinkScraper(i,driver));
				tagScraperList.add(this.createTagScraper(i, driver));
			}
//링크 스레드 구동
			for(ThreadHashTagLinkScraper scraper: linkScraperList) {
				printMessage(scraper.getName()+"이(가) 구동 되었습니다.(링크)");
				scraper.start();		
				try {
					Thread.sleep(THREAD_DELAY_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//링크 스레드 조인
			
			try {
				for(ThreadHashTagLinkScraper scraper: linkScraperList) scraper.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//태그 스레드 구동
			for(ThreadHashTagScraper scraper: tagScraperList) {
				printMessage(scraper.getName()+"이(가) 구동 되었습니다.(태그)");
				scraper.start();		
				try {
					Thread.sleep(THREAD_DELAY_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				for(ThreadHashTagScraper scraper: tagScraperList) scraper.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}catch(Exception e) {
			errMessage("수집 중에 알 수 없는 에러가 발생하였습니다.");
			e.printStackTrace();
			
		}finally {
			for(WebDriver driver: driverList) {
				driver.close();
				driver.quit();
			}
		}
	}
	
	public synchronized String getKeword() throws NoSuchElementException {
		return kewordList.poll();
	}
	public synchronized void addHashTagAll(LinkedList<HashTagDTO> hashTagList) {
		this.hashTagList.addAll(hashTagList);
	}
	public synchronized HashTagDTO getHashTag() throws NoSuchElementException {
		return hashTagList.poll();
	}
	public synchronized void getUrl(WebDriver driver, String url) {
		driver.get(url);
	}
	/**
	 * <pre> 해시태그 링크 스크래퍼(스레드) 생성
	 * @param num
	 * @return
	 */
	private ThreadHashTagLinkScraper createLinkScraper(int num,WebDriver driver) {
		return new ThreadHashTagLinkScraper("Scraper "+num,this,driver,loginCode);
	}
	/**
	 * <pre> 해시태그 내용 스크래퍼(스레드) 생성
	 * @param num
	 * @return
	 */
	private ThreadHashTagScraper createTagScraper(int num,WebDriver driver) {
		return new ThreadHashTagScraper("Scraper "+num,this,driver);
	}
	
	/**
	 * <pre> 크롬 드라이버 세팅 </pre>
	 */
	private WebDriver driverSetting() {
		String webDriverID = MetaDataLoader.getSeleniumProfile().getProperty("WEB_DRIVER_ID");
		String webDriverPath = MetaDataLoader.getSeleniumProfile().getProperty("WEB_DRIVER_PATH");
		System.setProperty(webDriverID,webDriverPath);
		WebDriver driver;
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		driver.manage().deleteAllCookies(); 
		return driver;
	}
	/**
	 * 
	 *<pre> 스레드 이름을 담은 평시 메시지 출력 </pre>
	 * @param message
	 */
	public void printMessage(String message) {
		System.out.println("[System] "+ message);
	}	
	
	
	/**
	 * 
	 *<pre> 스레드 이름을 담은 에러 메시지 출력 </pre>
	 * @param message
	 */
	public void errMessage(String message) {
		System.err.println("[System] "+ message);
	}
	
	
}
