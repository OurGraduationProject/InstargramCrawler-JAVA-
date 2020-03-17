package model.bl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import model.dto.KewordDTO;
 

public class InstargramScrapController {
	private int threadNum;
	private KewordMaker kewordMaker;
	private LinkedList<KewordDTO> kewordList;
	List<ThreadInstargamScraper> threadList;
	
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final int REPEAT_LIMIT = 10; // 스크롤 반복 횟수 제한(10번 반복동안 데이터 수 변환 없으면 그만 내림)
	public static final int COMMENT_UNIT = 12;//기본 댓글 출력 개수
	public static final int hashSearchCount = 1;
	
	public InstargramScrapController(int threadNum, List<String> dongList) {
		this.threadNum = threadNum;
		this.kewordMaker = new KewordMaker();
		this.kewordList = (LinkedList<KewordDTO>) this.kewordMaker.make(dongList);
		if(new ThreadInstargamScraper("Scraper 0",this).instargramScrap()) {
			this.threadList = new ArrayList<ThreadInstargamScraper>();
			for(int i=1; i<=this.threadNum; i++) {
				threadList.add(createThreadScraper(i));
			}
		}
	}
	
	public ThreadInstargamScraper createThreadScraper(int num) {
		return new ThreadInstargamScraper("Scraper "+num,this);
	}
	
	public synchronized KewordDTO getKeword() throws NoSuchElementException {
		return kewordList.poll();
	}
	
	public void startScrap() {
		
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
