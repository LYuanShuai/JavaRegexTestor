package code.tool.regex.data;

import java.util.Vector;

public class GroupManager {
	
	private Vector<ResultGroup> groups;
	
	private static GroupManager mInstance;
	
	public static GroupManager getInstance() {
		if(mInstance == null) {
			mInstance = new GroupManager();
		}
		return mInstance;
	}
	
	private GroupManager() {
		this.groups = new Vector<ResultGroup>();
	}
	
	public void addGroup(ResultGroup group) {
		if(this.groups.contains(group)) {
			return;
		}
		group.setIndex(this.groups.size());
		this.groups.add(group);
	}
	
	public void addGroup(int start, int end, int wrap) {
		ResultGroup group = new ResultGroup();
		group.setStart(start);
		group.setEnd(end);
		
		ResultGroup last = GroupManager.getInstance().checkoutLastGroup();
		if(last == null) {
			group.setCommence(0);
			group.setClosure(end - start);
			group.setIndex(0);
			this.groups.add(group);
			return;
		}
		group.setIndex(this.groups.size());
		group.setCommence(last.getClosure() + 1);
		group.setClosure(last.getClosure() + end - start + 1);
		this.groups.add(group);
	}
	
	public void removeGroup(ResultGroup group) {
		this.groups.remove(group);
	}
	
	public void updateGroup(ResultGroup group) {
		ResultGroup tg = findGroup(group.getStart(), group.getEnd());
		if(tg == null) {
			return;
		}
		tg.setCommence(group.getCommence());
		tg.setClosure(group.getClosure());
	}
	
	public ResultGroup detectWithin(int postion) {
		for(ResultGroup group : this.groups) {
			if(group.contains(postion)) {
				return group;
			}
		}
		return null;
	}
	
	public ResultGroup checkoutLastGroup() {
		if(this.groups.size() == 0) {
			return null;
		}
		return this.groups.lastElement();
	}
	
	public ResultGroup findGroup(int start, int end) {
		for(ResultGroup group : this.groups) {
			if(group.getStart() == start && group.getEnd() == end) {
				return group;
			}
		}
		return null;
	}
	
	public Integer findGroupIndex(int start, int end) {
		int in = this.groups.size();
		for(int i = 0; i < in; i++) {
			ResultGroup group = this.groups.elementAt(i);
			if(group.getStart() == start && group.getEnd() == end) {
				return i;
			}
		}
		return null;
	}
	
	public void resetGroups() {
		this.groups.clear();
	}
}
