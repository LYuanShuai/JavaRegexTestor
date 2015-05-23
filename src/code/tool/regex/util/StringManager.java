package code.tool.regex.util;

public class StringManager {
	private static StringManager mInstance;
	
	public static StringManager getInstance() {
		if(mInstance == null) {
			mInstance = new StringManager();
		}
		return mInstance;
	}
	
	private StringManager() {
		
	}
	
	public String replaceAll(String sou, String regex, String dest) {
		if(sou == null || sou.equals("")) {
			return null;
		}
		return sou.replaceAll(regex, dest);
	}
}
