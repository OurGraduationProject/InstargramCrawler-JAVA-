package util.kakaoLocalSearch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import com.google.gson.Gson;
/**
 * <strong>카카오 오픈 api 서비스 클래스</strong>
 * <pre>사용하기 전에 kakaoAPI.properties 파일을 수정할 것.</pre>
 * <pre>Gson필요</pre>
 */
public class KakaoAPIService {
	/*
	 * 
	 * profileName = API 필요 정보가 저장된 properties
	 * key = 애플리케이션 key값
	 * proFile = API 필요 정보를 로드할 객체
	 */
	private final String proFileName = "kakaoAPI.properties"; 
	protected String restKey, nativeKey, javaScriptKey, adminKey; 
	protected Properties proFile; 
	/**
	 * <strong>기본 생성자</strong> 
	 * <pre>카테고리 정보 로딩과, 키 값을 초기화<pre>
	 * 
	 * @author dongdang
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public KakaoAPIService() throws FileNotFoundException, IOException {
		super();
		proFile = new Properties();
		proFile.load(new FileInputStream(proFileName));
		this.restKey = proFile.getProperty("restKey");
		this.nativeKey = proFile.getProperty("nativeKey");
		this.javaScriptKey = proFile.getProperty("javaScriptKey");
		this.adminKey = proFile.getProperty("adminKey");
	}

	//getters, setters
	/**
	 * @return the proFileName
	 */
	public String getProFileName() {
		return proFileName;
	}
	/**
	 * @return the nativeKey
	 */
	public String getNativeKey() {
		return nativeKey;
	}
	/**
	 * @return the javaScriptKey
	 */
	public String getJavaScriptKey() {
		return javaScriptKey;
	}
	/**
	 * @return the adminKey
	 */
	public String getAdminKey() {
		return adminKey;
	}
	/**
	 * @return the url
	 */
	public String getURL(String key) {
		
		return proFile.getProperty(key);
	}
	
	/** Get메소드, JSON 형식의 쿼리 전송및 응답 메소드 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object urlGetSend(String urlString,Class a) throws IOException {
		/*
		 * 쿼리 진행 순서
		 * 1. 메소드 형식 및  요구 사항 설정
		 * 2. JSON 요청 및 응답
		 * 3. 응답 내용 읽기
		 * 4. GSON을 통하여 DTO 객체와 매칭
		 */
		
		//1
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json; ");
        con.setRequestProperty("Authorization","KakaoAK "+restKey.trim());
        
        //2
        con.setDoInput(true);
        int responseCode=con.getResponseCode();
        
        //3
        BufferedReader br;
        if(responseCode==200)  // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        else throw new IOException(responseCode+" : "+con.getResponseMessage());

        //4
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) response.append(inputLine);
        br.close();
        return new Gson().fromJson(response.toString(),a);
	}
	
	
	
}
