package code.tool.regex.util;

public class RegexMethods {
	private static RegexMethods mInstance;
	
	public static RegexMethods getInstance() {
		if(mInstance == null) {
			mInstance = new RegexMethods();
		}
		return mInstance;
	}
	
	private RegexMethods() {
		
	}
	
	public <T, E> boolean checkEqual(T t, E e) {
		if(t == null && e == null) {
			return true;
		}
		
		if(t == null || e == null) {
			return false;
		}
		
		return t.equals(e);
	}
	
	public <T> boolean contains(T[] ts, T t) {
		for(T tt : ts) {
			if(RegexMethods.getInstance().checkEqual(tt, t)) {
				return true;
			}
		}
		return false;
	}
}
