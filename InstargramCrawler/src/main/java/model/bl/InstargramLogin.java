package model.bl;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import model.dto.LoginInfoDTO;
import model.exception.LoginException;
import util.MetaDataLoader;


public class InstargramLogin {
	//페이스북 로그인 URL
	private String loginPage = "https://www.facebook.com/login.php?skip_api_login=1&api_key=124024574287414&kid_directed_site=0&app_id=124024574287414&signed_next=1&next=https%3A%2F%2Fwww.facebook.com%2Fdialog%2Foauth%3Fclient_id%3D124024574287414%26redirect_uri%3Dhttps%253A%252F%252Fwww.instagram.com%252Faccounts%252Fsignup%252F%26state%3D%257B%2522fbLoginKey%2522%253A%2522so6fc9m7kbq91eh5gua12ii2g3m6pdnx1c52j9b6watvl1xdzm1b%2522%252C%2522fbLoginReturnURL%2522%253A%2522%252F%2522%257D%26scope%3Demail%26response_type%3Dcode%252Cgranted_scopes%26locale%3Dko_KR%26ret%3Dlogin%26fbapp_pres%3D0%26logger_id%3Dbe3e08ed-639e-4a26-a2cf-389966efc80d&cancel_url=https%3A%2F%2Fwww.instagram.com%2Faccounts%2Fsignup%2F%3Ferror%3Daccess_denied%26error_code%3D200%26error_description%3DPermissions%2Berror%26error_reason%3Duser_denied%26state%3D%257B%2522fbLoginKey%2522%253A%2522so6fc9m7kbq91eh5gua12ii2g3m6pdnx1c52j9b6watvl1xdzm1b%2522%252C%2522fbLoginReturnURL%2522%253A%2522%252F%2522%257D%23_%3D_&display=page&locale=ko_KR&pl_dbl=0\r\n";
	//계정 정보 객체
	private LoginInfoDTO loginInfoDTO;
	/**
	 * 생성자 -계정 세팅
	 * 
	 */
	public InstargramLogin() {
		// TODO Auto-generated constructor stub
		accountSetting();
	}
	/**
	 * <pre>계정 세팅</pre>
	 */
	public void accountSetting() {
		Properties accountInfo = MetaDataLoader.getIdProfile();
		String id = accountInfo.getProperty("id");
		String pwd = accountInfo.getProperty("pwd");
		loginInfoDTO = new LoginInfoDTO(id,pwd);
	}
	/**
	 * <pre>페이스북 계정 로그인</pre>
	 */
	public int login(WebDriver driver) throws LoginException {
		try {
			
			driver.get(loginPage);
			driver.findElement(By.id(Tag.id_id)).sendKeys(loginInfoDTO.getId());
			driver.findElement(By.id(Tag.id_pwd)).sendKeys(loginInfoDTO.getPwd());
			driver.findElement(By.id(Tag.id_pwd)).sendKeys(Keys.RETURN);
			driver.findElement(By.xpath(Tag.xpath_continue)).click();
			driver.findElement(By.xpath(Tag.xpath_afterwards)).click();
        } catch (Exception e) {
        	throw new LoginException();
        }
		return 0;
	}
	/**
	 * <pre>연결 정보 끊길 시 재접속</pre>
	 */
	public int errorHandling(WebDriver driver) {
		driver.findElement(By.xpath(Tag.xpath_continue)).click();
		driver.findElement(By.xpath(Tag.xpath_afterwards)).click();
		return 0;
	}
	/**
	 * <pre>태그 정보</pre>
	 * @author dongdang
	 *
	 */
	private interface Tag {
		String id_id = "email";
		String id_pwd = "pass";
		String xpath_continue = "//*[@id=\"react-root\"]/section/main/article/div[2]/div[1]/div/div[2]/button ";
		String xpath_afterwards = "/html/body/div[4]/div/div/div[3]/button[2]";
	}
	

	
}
