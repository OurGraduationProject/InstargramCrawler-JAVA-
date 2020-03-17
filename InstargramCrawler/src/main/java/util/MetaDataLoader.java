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
	static {
		try {
			seleniumProfile.load(new FileInputStream("seleniumInfo.properties"));	
			accountProfile.load(new FileInputStream("account.properties"));
			dbInfoProfile.load(new FileInputStream("dbinfo.properties"));
			dbCmdProfile.load(new FileInputStream("dbcmd.properties"));
			DBConnManager.dbLoading(dbInfoProfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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

	
	
	
}
