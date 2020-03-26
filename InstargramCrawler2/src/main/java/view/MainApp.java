package view;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import model.bl.InstargramLogin;
import model.bl.InstargramScrap;
import model.exception.LoginException;
import util.DBConnManager;
import util.MetaDataLoader;
import util.nsdi.CtprvnSearch;
import model.bl.InstargramScrapController;
import model.bl.KewordMaker;

public class MainApp {

	public static void main(String[] args) {
/*
 * readMe!!!!!!!!!!
 * 크롤러 설정 값은 InstargramScrapController 클래스에서 제어.
 * 수집의 기준이 되는 값은 메인 메소드의 상수 값을 변경하여 제어.
 * 메인 메소드 시작 전 account.properties, dbinfo 확인.
 */

		
		final String ctprvn = "경기도";  //시도
		final String signgu = "안양시";  //시군구
		final int loginCode = InstargramLogin.DEFAULT_CODE;
//인스타그램 로그인 : InstargramLogin.DEFAULT_CODE
//페이스북 소셜 로그인  : InstargramLogin.FACEBOOK_CODE;
		final int threadNum = 3; //스레드 수
		final boolean directInput = true;
//true -> 직접입력.
//false -> 해당 지역의 전체정보를 대상으로 함.
		
		LinkedList<String> list = null;
		
		
//직접 입력 (동 변경시 ctprvn, signgu 확인.)				
		if(directInput) {
			
			list = new LinkedList<String>();
			list.add("석수동");//스크랩트할 법정동 추가
		}
//자동 입력		
		else {
			try {
				list = new KewordMaker().dongMake(ctprvn, signgu);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//같은 지역에 있는 지하철 검색.
		try {
			KewordMaker km = new KewordMaker();
			list.addAll(km.subwayMake(signgu,list));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		System.out.println(list);

//크롤러
		InstargramScrapController isc = new InstargramScrapController(threadNum,list,loginCode); //스레드 갯수와 스크랩할 법정동리스트
		isc.startScrap();

		

	}
}
