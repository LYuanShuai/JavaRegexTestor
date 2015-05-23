package code.tool.regex.data;

public class ResultGroup {
	private Integer start;
	private Integer end;
	
	private Integer commence;
	private Integer closure;
	
	private Integer index;
	
	public ResultGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResultGroup(Integer start, Integer end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getCommence() {
		return commence;
	}

	public void setCommence(Integer commence) {
		this.commence = commence;
	}

	public Integer getClosure() {
		return closure;
	}

	public void setClosure(Integer closure) {
		this.closure = closure;
	}
	
	public boolean contains(int position) {
		return position >= commence && position <= closure;
	}
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getLength() {
		return this.end - this.start;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ResultGroup) {
			ResultGroup g = (ResultGroup) obj;
			
			return this.start == g.getStart()
					&& this.end == g.getEnd()
					&& this.commence == g.getCommence()
					&& this.closure == g.getClosure();
		}
		return false;
	}
	
	public String toString() {
		return "Index: "    + this.index     + "; " +
			   "Start: "    + this.start     + "; " +
			   "End: "      + this.end       + "; " +
			   "Commence: " + this.commence  + "; " +
			   "Closure: "  + this.closure;
	}
}
