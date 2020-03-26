package util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MetaDataLoader {
	
	private static Properties seleniumProfile = new Properties();
	private static Properties accountProfile = new Properties();
	private static Properties dbInfoProfile = new Properties();
	private static Properties dbCmdProfile = new Properties();
	private static Properties nsdiProfile = new Properties(); 
	static {
		try {
			seleniumProfile.load(new FileInputStream("seleniumInfo.properties"));	
			accountProfile.load(new FileInputStream("account.properties"));
			dbInfoProfile.load(new FileInputStream("dbinfo.properties"));
			dbCmdProfile.load(new FileInputStream("dbcmd.properties"));
			nsdiProfile.load(new FileInputStream("nsdiInfo.properties"));
			DBConnManager.dbLoading(dbInfoProfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("메타 데이터 파일이 존재하지 않습니다.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	/**
	 * @return the seleniumProfile
	 */
	public static Properties getSeleniumProfile() {
		return seleniumProfile;
	}
	/**
	 * @return the idProfile
	 */
	public static Properties getIdProfile() {
		return accountProfile;
	}
	public static Properties getDbCmdProfile() {
		return dbCmdProfile;
	}
	public static Properties getNsdiProfile() {
		return nsdiProfile;
	}

	
	
	
}
