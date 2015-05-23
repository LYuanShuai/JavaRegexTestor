package code.tool.regex.style;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import code.tool.regex.util.RegexManager;

public class RegexPaneStyle extends DefaultStyledDocument {
	
	private static final long serialVersionUID = -7441374207132266969L;
	
	public RegexPaneStyle() {
		
	}
	
	@Override
	protected void insertUpdate(AbstractDocument.DefaultDocumentEvent chng, AttributeSet attr) {
		try {
			String regex = chng.getDocument().getText(0, getLength());
			if(regex == null || regex.equals("")) {
				return;
			}
			boolean validate = RegexManager.getInstance().check_escape_character_legal(regex);
			if(validate) {
				// System.out.println("correct input, earse all underline.");
				StyleManager.getInstance().attributeString(this, 0, getLength(), StyleManager.getInstance().getRegexNormalStyle());
			}
			else {
				// System.out.println("wrong input, add underline.");
				StyleManager.getInstance().attributeString(this, 0, getLength(), StyleManager.getInstance().getRegexWrongStyle());
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
