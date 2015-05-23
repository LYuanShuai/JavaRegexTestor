package code.tool.regex.test;

import code.tool.regex.util.RegexMethods;

public class RegexMethodsTest {

	public RegexMethodsTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void init() {
		
	}
	
	public void test_boolean_checkEqual_T_E() {
		System.out.println(RegexMethods.getInstance().checkEqual(1, 2));
		System.out.println(RegexMethods.getInstance().checkEqual(null, null));
	}
	
	public void test_boolean_contain_LT_T() {
		Integer[] ts = new Integer[] {1,2,3,4,5};
		System.out.println(RegexMethods.getInstance().contains(ts, 2));
	}
	
	public static void main(String[] args) {
		RegexMethodsTest rmt = new RegexMethodsTest();
		rmt.init();
		// rmt.test_boolean_checkEqual_T_E();
		rmt.test_boolean_contain_LT_T();
	}
}
