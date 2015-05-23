package code.tool.regex;

public interface HilightListener {
	public void onNewGroupMatched(String group, int start, int end);
	public void onMatchEnd();
	public void onMatchError(String errorMessage);
}
