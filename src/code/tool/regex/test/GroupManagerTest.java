package code.tool.regex.test;

import code.tool.regex.data.GroupManager;
import code.tool.regex.data.ResultGroup;

public class GroupManagerTest {

	public GroupManagerTest() {
		
	}
	
	public void init() {
		
	}
	
	public void test_void_updateGroup_Group() {
		ResultGroup group = new ResultGroup();
		group.setStart(0);
		group.setEnd(12);
		group.setCommence(0);
		group.setClosure(13);
		GroupManager.getInstance().addGroup(group);
		
		ResultGroup ygroup = new ResultGroup();
		ygroup.setStart(0);
		ygroup.setEnd(12);
		ygroup.setCommence(1520);
		ygroup.setClosure(1869);
		GroupManager.getInstance().updateGroup(ygroup);
		
		System.out.println(GroupManager.getInstance().findGroup(0, 12).getCommence());
	}
	
	public static void main(String[] args) {
		GroupManagerTest gmt = new GroupManagerTest();
		gmt.init();
		gmt.test_void_updateGroup_Group();
	}
}
