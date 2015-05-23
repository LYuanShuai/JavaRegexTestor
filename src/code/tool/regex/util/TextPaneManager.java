package code.tool.regex.util;

import javax.swing.JTextPane;
import code.tool.regex.style.StyleManager;

public class TextPaneManager {
	private static String ENTER;
	
	private static TextPaneManager mInstance;
	
	public static TextPaneManager getInstance() {
		if(mInstance == null) {
			mInstance = new TextPaneManager();
		}
		return mInstance;
	}
	
	private TextPaneManager() {
		ENTER = System.getProperty("line.separator");
	}
	
	public void append(JTextPane textPane, String text, boolean newLine) {
		if(textPane == null) {
			return;
		}
		String textPane_text = textPane.getText();
		// System.out.println("append, show old text here == " + textPane_text);
		if(textPane_text == null) {
			textPane_text = "";
		}
		if(textPane_text.equals("")) {
			textPane.setText(textPane_text + text);
			return;
		}
		if(newLine) {
			textPane_text += "\n";
		}
		textPane.setText(textPane_text + text);
	}
	
	public void clear(JTextPane textPane) {
		if(textPane == null) {
			return;
		}
		textPane.setText("");
		StyleManager.getInstance().resetPane(textPane.getStyledDocument());
	}
	
	public void replaceAllEnterToWrap(JTextPane textPane) {
		if(textPane == null) {
			return;
		}
		
		String textPane_text = textPane.getText();
		// System.out.println("show text pane text here\n and string length " + textPane_text.length() + "\n" + textPane_text);
		if(textPane_text == null || textPane_text.equals("")) {
			return;
		}
		textPane_text = textPane_text.replaceAll(ENTER, "\n");
		// System.out.println("show text pane text here\n and string length " + textPane_text.length() + "\n" + textPane_text);
		textPane.setText(textPane_text);
	}
}
