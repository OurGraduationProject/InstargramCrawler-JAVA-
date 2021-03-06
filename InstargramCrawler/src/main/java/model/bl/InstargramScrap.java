package model.bl;



import java.awt.Container;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import model.dto.CommentDTO;
import model.dto.ContentDTO;
import model.dto.HashTagDTO;
import model.dto.SubHashTagDTO;


public class InstargramScrap {
	
	//초기 시작 URL
	private String seedURL = "https://www.instagram.com";
	//자신을 사용할 스크래퍼
	private ThreadInstargamScraper tis;
	//인스타그램 날짜 표현 형식을 설정할 객체
	private SimpleDateFormat dateFormat;
	
	/**
	 * 생성자 - 자신을 사용할 스크래퍼 설정
	 * @param tis
	 */
	public InstargramScrap(ThreadInstargamScraper tis) {
		// TODO Auto-generated constructor stub
		this.tis = tis;
		dateFormat = new SimpleDateFormat(InstargramScrapController.DATE_FORMAT);
	}
	
	/**
	 * <pre>검색리스트를 수집/pre>
	 * @param keword
	 * @param driver
	 * @return LinkedList
	 */
	public LinkedList<HashTagDTO> searchListScrap(String keword,WebDriver driver)  {
		
		LinkedList<HashTagDTO> searchList = new LinkedList<HashTagDTO>();
		
		driver.get(seedURL);
		driver.findElement(By.xpath(Tag.xpath_search)).clear();
		driver.findElement(By.xpath(Tag.xpath_search)).sendKeys(keword);
		int count = 0;
		for(WebElement tag : driver.findElements(By.xpath(Tag.xpath_searchList))) {
			String href = tag.getAttribute("href");
			if(href.contains("/tags/")) {
				HashTagDTO hashTagDTO = new HashTagDTO();
				String url = href.replace(seedURL,"");
				try {
					url = URLDecoder.decode(url,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					tis.errMessage("url을 디코딩하는데 문제가 발생했습니다.");
					e.printStackTrace();
				}
				hashTagDTO.setHashTagAdr(url);
				hashTagDTO.setHashTagNm(tag.findElement(By.cssSelector(Tag.css_searchListNm)).getText().replace("#", ""));
				String num = tag.findElement(By.cssSelector(Tag.css_searchListContent)).getText();
				num = num.replace(",", "");
				hashTagDTO.setContentNum(Integer.parseInt(num));
				searchList.add(hashTagDTO);
				count++;
			}
			if(count >= InstargramScrapController.hashSearchCount) break;
		}
	
		return searchList;
	
	}
	
	/**
	 * <pre> 게시물 링크 수집</pre>
	 * @param dto
	 * @param driver
	 */
	public void contentLinkScrap(HashTagDTO dto,WebDriver driver)  {
		/*
		 * 동적으로 스크롤 위치에 따라 배치되는 리스트(태그)가 다름.
		 * 따라서, hashset 자료구조를 통해 중복을 제거하면서 반복적으로 저장을 시도함. 
		 */
		driver.get(seedURL+dto.getHashTagAdr());
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		HashMap<String, ContentDTO> contentMap = new HashMap<String, ContentDTO>();
		int repeatNum = 0;
		while(repeatNum < InstargramScrapController.REPEAT_LIMIT) {
			int pastMapSize = contentMap.size();
			
			try {
				for(WebElement element : driver.findElements(By.cssSelector(Tag.css_listRow))) {
					try {
						for(WebElement element2 : element.findElements(By.cssSelector(Tag.css_listCol))) {
							try {
								String contentAdr = element2.findElement(By.cssSelector(Tag.css_contentAnchor)).getAttribute("href");
								contentAdr = contentAdr.replace(seedURL, "");
								try {
									contentAdr = URLDecoder.decode(contentAdr,"UTF-8");
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									tis.errMessage("url("+contentAdr+")을 디코딩하는데 문제가 발생했습니다.");
									e.printStackTrace();
								}
								if(contentMap.get(contentAdr)!=null) continue; 
								ContentDTO content = new ContentDTO();
								content.setContentAdr(contentAdr);
								try {
									Actions mouseOver = new Actions(driver).moveToElement(element2);
									mouseOver.perform();
								}catch(NoSuchElementException | StaleElementReferenceException e) {}
								String likeStr = "0";
								String commentStr = "0";
								try {
									likeStr = element2.findElement(By.cssSelector(Tag.css_listColLike)).getText();
									content.setGood(Integer.parseInt(likeStr.replaceAll(",", "")));
								}catch(NoSuchElementException | StaleElementReferenceException e) {
									
								}catch(NumberFormatException e) {
									likeStr = likeStr.replaceAll(".", "").replaceAll("천", "").replaceAll("만", "");
									content.setGood(Integer.parseInt(likeStr));
								}
								try {
									commentStr = element2.findElement(By.cssSelector(Tag.css_listColComment)).getText();
									content.setCommentNum(Integer.parseInt(commentStr.replaceAll(",", "")));
								}catch(NoSuchElementException | StaleElementReferenceException e) {
									
								}catch(NumberFormatException e) {
									likeStr = likeStr.replaceAll(".", "").replaceAll("천", "").replaceAll("만", "");
									content.setGood(Integer.parseInt(likeStr));
								}						
								
								contentMap.put(content.getContentAdr(), content);
								tis.printMessage(dto.getHashTagNm() +" [" +contentMap.size()+"/"+dto.getContentNum()+"] "+ " 링크 수집중....");
							}catch(NoSuchElementException | StaleElementReferenceException e) {
								tis.errMessage("게시물링크의 부재가 발생하였느나, 작업을 진행합니다.");
							}
						}
					}catch(StaleElementReferenceException e) {
//						tis.errMessage("게시물링크(열)의 부재가 발생하였느나, 작업을 진행합니다.");
					}
				}
			}catch(StaleElementReferenceException e) {
//				tis.errMessage("게시물링크(행)의 부재가 발생하였느나, 작업을 진행합니다.");
			}
			
			int curruntMapSize = contentMap.size();
			if(pastMapSize == curruntMapSize) repeatNum++;
			else repeatNum = 0;
			js.executeScript("window.scrollTo (0, document.body.scrollHeight)");	
		}
		dto.setContentMap(contentMap);
		tis.printMessage(dto.getHashTagNm() +" [" +contentMap.size()+"/"+dto.getContentNum()+"] "+ " 링크 수집 완료");
	}
	
	/**
	 * <pre>게시물 수집</pre>
	 * @param dto
	 * @param driver
	 */
	public void contentScrap(HashTagDTO dto,WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.MINUTES);
		HashMap<String,ContentDTO> map = dto.getContentMap();
		for(String key : map.keySet()) {
			ContentDTO content = map.get(key);
			tis.printMessage(content.getContentAdr()+"게시글을 수집합니다.");
			driver.get(seedURL+content.getContentAdr());
			String id = ""; 
			try {
				id = driver.findElement(By.cssSelector(Tag.css_contentId)).getText();
			}catch(NoSuchElementException | StaleElementReferenceException e) {
				tis.errMessage(content.getContentAdr()+"에서 작성자 id를 가져올 수 없습니다. 공백으로 채웁니다.");
			}
			String inContent = "";
			try {
				inContent = driver.findElement(By.cssSelector(Tag.css_contentContent)).getText();
			}catch(NoSuchElementException | StaleElementReferenceException e) {
				try {
					inContent = driver.findElement(By.cssSelector(Tag.css_contentContent2)).getText();
				}catch(NoSuchElementException | StaleElementReferenceException e1) {
					tis.errMessage(content.getContentAdr()+"에서 내용을 가져올 수 없습니다. 공백으로 채웁니다.");
				}
			}
			String time = "";
			try {
				time = driver.findElement(By.xpath(Tag.xpath_contentDate)).getAttribute("datetime");
			}catch(NoSuchElementException | StaleElementReferenceException e) {
				tis.errMessage(content.getContentAdr()+"에서 작성날짜를 가져올 수 없습니다. 공백으로 채웁니다.");
			}
			for(int i=0; i<content.getCommentNum()/InstargramScrapController.COMMENT_UNIT; i++) {
				try {
					driver.findElement(By.cssSelector(Tag.css_contentCommentLoad)).click();
				}catch(NoSuchElementException | StaleElementReferenceException | ElementNotInteractableException e) {}
			}
			List<CommentDTO> commentList = new ArrayList<CommentDTO>();
			if(content.getCommentNum()>0) {
				for(WebElement element : driver.findElements(By.cssSelector(Tag.css_contentComment))) {
					String commentId = element.findElement(By.cssSelector(Tag.css_contentCommentId)).getText();
					String commentContent = element.findElement(By.cssSelector(Tag.css_contentCommentContent)).getText();
					String commentTime = element.findElement(By.cssSelector(Tag.css_contentCommentDate)).getAttribute("datetime");
					String contentAdr = content.getContentAdr();
					CommentDTO comment = new CommentDTO();
					
					comment.setId(commentId);
					comment.setContent(commentContent);
					comment.setContentAdr(contentAdr);
					try {
						comment.setCreationDate(new Date(dateFormat.parse(commentTime).getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						tis.errMessage(content.getContentAdr()+"의 댓글에서 시간을 변환하는데 오류가 발생하였습니다.");
					}
					commentList.add(comment);
				}
				
			}
			List<SubHashTagDTO> subHashList = new ArrayList<SubHashTagDTO>();
			for(WebElement element :  driver.findElements(By.cssSelector(Tag.css_contentSubHash))) {
				SubHashTagDTO subHash = new SubHashTagDTO();
				subHash.setContentAdr(content.getContentAdr());
				subHash.setHashTagNm(element.getText().replace("#", ""));
				subHashList.add(subHash);
			}
			content.setId(id);
			content.setContent(inContent);
			try {
				content.setCreationDate(new Date(dateFormat.parse(time).getTime()));
			} catch (ParseException e) {
				tis.errMessage(content.getContentAdr()+"에서 시간을 변환하는데 오류가 발생하였습니다.");
			}
			content.setSubHashTagList(subHashList);
			content.setCommentList(commentList);
			driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		}
	}
	
	/**
	 * <pre> 태그 상수 </pre>
	 * @author dongdang
	 *
	 */
	private interface Tag {
		String xpath_search = "//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/input";
		String xpath_searchList = "//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a";
//		String xpath_stopMessage ="//*[@id=\"react-root\"]/section/main/article/div[2]/p";
		String css_searchListContent = "div > div > div.Fy4o8 > span > span";
		String css_searchListNm="div > div > div.uyeeR > span";
		String css_listRow="div.Nnq7C";
		String css_listCol="div.v1Nh3";
		String css_listColLike ="li:nth-child(1) > span:nth-child(1)";
		String css_listColComment = "li:nth-child(2) > span:nth-child(1)";
		String css_contentAnchor="a";
		
		String css_contentId = "header a.sqdOP";
		String css_contentContent = "ul.XQXOT > div > li > div > div > div > span";
		String css_contentContent2 = "ul.XQXOT > div > li > div > div > div > h1";
		String xpath_contentDate = "//*[@id=\"react-root\"]/section/main/div/div[1]/article/div[2]/div[2]/a/time";
		String css_contentComment="ul.Mr508 >div >li >div > div > div.C4VMK";
		String css_contentCommentId = "h3 > div > a";
		String css_contentCommentContent = "span";
		String css_contentCommentDate = "div > div > a > time";
		String css_contentCommentLoad = "button.dCJp8";
		String css_contentSubHash = "a.xil3i";
		
	}
	
}
