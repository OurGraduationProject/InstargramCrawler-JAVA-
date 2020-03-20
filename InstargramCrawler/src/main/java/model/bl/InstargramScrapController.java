package model.bl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;

import model.dto.KewordDTO;
 

public class InstargramScrapController {
	//스레드 개수
	private int threadNum;
	//상가명, 법정동, 법정동 상가명을 만드는 객체
	private KewordMaker kewordMaker;
	//키워드 리스트
	private LinkedList<KewordDTO> kewordList;
	//스레드 리스트
	private List<ThreadInstargamScraper> threadList;
	
	
	//인스타그램 날짜 포맷
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	// 스크롤 반복 횟수 제한(10번 반복동안 데이터 수 변환 없으면 그만 내림)
	public static final int REPEAT_LIMIT = 10; 
	//기본 댓글 출력 개수
	public static final int COMMENT_UNIT = 12;
	//검색리스트 갯수 제한 
	public static final int hashSearchCount = 3;
	
	/**
	 * 생성자 - 키워드 리스트 생성
	 * @param threadNum
	 * @param dongList
	 */
	public InstargramScrapController(int threadNum, List<String> dongList) {
		this.threadNum = threadNum;
		this.kewordMaker = new KewordMaker();
		this.kewordList = (LinkedList<KewordDTO>) this.kewordMaker.make(dongList);
		
	}

	/**
	 * <pre> 스크래퍼(스레드) 생성
	 * @param num
	 * @return
	 */
	public ThreadInstargamScraper createThreadScraper(int num) {
		return new ThreadInstargamScraper("Scraper "+num,this);
	}

	/**
	 * <pre> 스크래퍼가 키워드를 가져가는 메소드<pre>
	 * 
	 * @return
	 * @throws NoSuchElementException
	 */
	public synchronized KewordDTO getKeword() throws NoSuchElementException {
		return kewordList.poll();
	}
	
	public synchronized void getUrl(WebDriver driver, String url) {
		driver.get(url);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre> 스레드 구동 </pre>
	 */
	public void startScrap() {
		if(new ThreadInstargamScraper("Scraper 0",this).instargramScrap()) {
			this.threadList = new ArrayList<ThreadInstargamScraper>();
			for(int i=1; i<=this.threadNum; i++) {
				threadList.add(createThreadScraper(i));
			}
			for(ThreadInstargamScraper scraper : threadList) {
				scraper.start();
				System.out.println("[System] "+ scraper.getName()+" 구동 되었습니다.");
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
