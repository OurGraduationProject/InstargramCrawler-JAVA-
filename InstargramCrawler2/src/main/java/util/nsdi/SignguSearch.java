package util.nsdi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import util.nsdi.dto.AdmListResponseDTO;


public class SignguSearch {
	
	private final String URL = "http://openapi.nsdi.go.kr/nsdi/eios/service/rest/AdmService/admSiList.json";
	private final String KEY = "cb85ce17b97d3498d9e8d0";
	public SignguSearch() {
		// TODO Auto-generated constructor stub
	}
	
	public AdmListResponseDTO urlGetSend(int code) throws IOException {
	
		URL url = new URL(URL+"?" + URLEncoder.encode("authkey","UTF-8")+"=" + KEY+"&"+URLEncoder.encode("admCode","UTF-8") + "=" + URLEncoder.encode(String.valueOf(code),"UTF-8"));
        
        
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json; ");
		
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
        return new Gson().fromJson(response.toString(),AdmListResponseDTO.class);
	}
}
