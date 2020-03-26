package model.bl;

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
	
	//자신을 사용할 스크래퍼
	private ThreadHashTagLinkScraper linkScraper;
	private ThreadHashTagScraper tagScraper;
	//인스타그램 날짜 표현 형식을 설정할 객체
	private SimpleDateFormat dateFormat;
	
	/**
	 * 생성자 - 자신을 사용할 스크래퍼 설정
	 * @param tis
	 */
	public InstargramScrap() {
		// TODO Auto-generated constructor stub
		dateFormat = new SimpleDateFormat(InstargramScrapController.DATE_FORMAT);
	}
	public InstargramScrap(ThreadHashTagScraper tagScraper) {
		// TODO Auto-generated constructor stub
		this();
		this.tagScraper = tagScraper;
	}
	public InstargramScrap(ThreadHashTagLinkScraper linkScraper) {
		// TODO Auto-generated constructor stub
		this();
		this.linkScraper = linkScraper;
	}
	/**
	 * <pre>검색리스트를 수집/pre>
	 * @param keword
	 * @param driver
	 * @return LinkedList
	 */
	public LinkedList<HashTagDTO> searchListScrap(String keword,WebDriver driver) {
		String seedURL = InstargramScrapController.seedURL;
		LinkedList<HashTagDTO> searchList = new LinkedList<HashTagDTO>();
		
		linkScraper.getUrl(seedURL);
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
					linkScraper.errMessage("url을 디코딩하는데 문제가 발생했습니다.");
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
			int hashSearchCount = InstargramScrapController.hashSearchCount;
			if(hashSearchCount>0) {
				if(count>hashSearchCount) break;
			}
			
		}
	
		return searchList;
	
	}

	/**
	 * <pre> 게시물 링크 수집</pre>
	 * @param dto
	 * @param driver
	 */
	public void contentLinkScrap(HashTagDTO dto,WebDriver driver) {
		
		String seedURL = InstargramScrapController.seedURL;
		tagScraper.getUrl(seedURL+dto.getHashTagAdr());
		
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
									tagScraper.errMessage("url("+contentAdr+")을 디코딩하는데 문제가 발생했습니다.");
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
									element2.findElement(By.cssSelector(Tag.css_listcolLikeImange));
									likeStr = element2.findElement(By.cssSelector(Tag.css_listColLike)).getText();
									content.setGood(Integer.parseInt(likeStr.replaceAll(",", "").replace(".", "").replace("천", "00").replace("만", "000")));
								}catch(NoSuchElementException | StaleElementReferenceException e) {
									tagScraper.errMessage("좋아요 수가 없어서 건너뜁니다.");
									continue;
								}catch(NumberFormatException e) {
									content.setGood(0);
									tagScraper.errMessage("숫자변환 예외가 발생하여 0으로 초기화하고 작업을 진행합니다.");
								}
								try {
									commentStr = element2.findElement(By.cssSelector(Tag.css_listColComment)).getText();
									content.setCommentNum(Integer.parseInt(commentStr.replace(",", "").replace(".", "").replace("천", "").replace("만", "")));
								}catch(NoSuchElementException | StaleElementReferenceException e) {
									
								}catch(NumberFormatException e) {
									tagScraper.errMessage("숫자변환 예외가 발생하여 0으로 초기화하고 작업을 진행합니다.");
								}						
								
								contentMap.put(content.getContentAdr(), content);
								tagScraper.printMessage(dto.getHashTagNm() +" [" +contentMap.size()+"/"+dto.getContentNum()+"] "+ " 링크 수집중....");
							}catch(NoSuchElementException | StaleElementReferenceException e) {
								tagScraper.errMessage("게시물링크의 부재가 발생하였느나, 작업을 진행합니다.");
							}
						}
					}catch(StaleElementReferenceException e) {
//						tagScraper.errMessage("게시물링크(열)의 부재가 발생하였느나, 작업을 진행합니다.");
					}
				}
			}catch(StaleElementReferenceException e) {
//				tagScraper.errMessage("게시물링크(행)의 부재가 발생하였느나, 작업을 진행합니다.");
			}
			
			int curruntMapSize = contentMap.size();
			if(pastMapSize == curruntMapSize) repeatNum++;
			else repeatNum = 0;
			js.executeScript("window.scrollTo (0, document.body.scrollHeight)");	
		}
		dto.setContentMap(contentMap);
		tagScraper.printMessage(dto.getHashTagNm() +" [" +contentMap.size()+"/"+dto.getContentNum()+"] "+ " 링크 수집 완료");
	}
	
	/**
	 * <pre>게시물 수집</pre>
	 * @param dto
	 * @param driver
	 */
	public void contentScrap(HashTagDTO dto,WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(1,TimeUnit.MINUTES);
		HashMap<String,ContentDTO> map = dto.getContentMap();
		String seedURL = InstargramScrapController.seedURL;
		for(String key : map.keySet()) {
			ContentDTO content = map.get(key);
			tagScraper.printMessage(content.getContentAdr()+"게시글을 수집합니다.");
			tagScraper.getUrl(seedURL+content.getContentAdr());
			String id = ""; 
			try {
				id = driver.findElement(By.cssSelector(Tag.css_contentId)).getText();
			}catch(NoSuchElementException | StaleElementReferenceException e) {
				tagScraper.errMessage(content.getContentAdr()+"에서 작성자 id를 가져올 수 없습니다. 공백으로 채웁니다.");
			}
			String inContent = "";
			try {
				inContent = driver.findElement(By.cssSelector(Tag.css_contentContent)).getText();
			}catch(NoSuchElementException | StaleElementReferenceException e) {
				try {
					inContent = driver.findElement(By.cssSelector(Tag.css_contentContent2)).getText();
				}catch(NoSuchElementException | StaleElementReferenceException e1) {
					tagScraper.errMessage(content.getContentAdr()+"에서 내용을 가져올 수 없습니다. 공백으로 채웁니다.");
				}
			}
			String time = "";
			try {
				time = driver.findElement(By.xpath(Tag.xpath_contentDate)).getAttribute("datetime");
			}catch(NoSuchElementException | StaleElementReferenceException e) {
				tagScraper.errMessage(content.getContentAdr()+"에서 작성날짜를 가져올 수 없습니다. 공백으로 채웁니다.");
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
						tagScraper.errMessage(content.getContentAdr()+"의 댓글에서 시간을 변환하는데 오류가 발생하였습니다.");
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
				tagScraper.errMessage(content.getContentAdr()+"에서 시간을 변환하는데 오류가 발생하였습니다.");
			}
			content.setSubHashTagList(subHashList);
			content.setCommentList(commentList);
		}
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
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
		String css_listcolLikeImange="span._1P1TY.coreSpriteHeartSmall";
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
