package view;

import java.util.ArrayList;
import java.util.List;

import model.bl.InstargramScrapController;

public class MainApp {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("석수동");//스크랩트할 법정동 추가

		InstargramScrapController isc = new InstargramScrapController(1,list); //스레드 갯수와 스크랩할 법정동리스트
		isc.startScrap();
	}
}
