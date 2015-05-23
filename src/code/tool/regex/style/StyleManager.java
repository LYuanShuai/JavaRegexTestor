package code.tool.regex.style;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class StyleManager {
	private static StyleManager mInstance;
	
	public static StyleManager getInstance() {
		if(mInstance == null) {
			mInstance = new StyleManager();
		}
		return mInstance;
	}
	
	private StyleManager() {
		
	}
	
	public void attributeString(StyledDocument doc, int pos, int len, AttributeSet attr) {
		if(doc == null) {
			return;
		}
		doc.setCharacterAttributes(pos, len, attr, true);
	}
	
	public void resetPane(StyledDocument doc) {
		if(doc == null) {
			return;
		}
		doc.setCharacterAttributes(0, doc.getLength(), new SimpleAttributeSet(), true);
	}
	
	public SimpleAttributeSet getRegexNormalStyle() {
		SimpleAttributeSet attr = new SimpleAttributeSet();
		// attr.addAttribute(StyleConstants.CharacterConstants.Underline, false);
		attr.addAttribute(StyleConstants.ColorConstants.Foreground, Color.BLACK);
		return attr;
	}
	
	public SimpleAttributeSet getRegexWrongStyle() {
		SimpleAttributeSet attr = new SimpleAttributeSet();
		// attr.addAttribute(StyleConstants.CharacterConstants.Underline, true);
		attr.addAttribute(StyleConstants.ColorConstants.Foreground, Color.RED);
		return attr;
	}
	
	public SimpleAttributeSet getMatchHilightItemStyle() {
		SimpleAttributeSet attr = new SimpleAttributeSet();
		attr.addAttribute(StyleConstants.ColorConstants.Background, Color.ORANGE);
		return attr;
	}
	
	public SimpleAttributeSet getMatchItemNormalStyle() {
		SimpleAttributeSet attr = new SimpleAttributeSet();
		attr.addAttribute(StyleConstants.ColorConstants.Background, Color.WHITE);
		return attr;
	}
	
	public SimpleAttributeSet getMatchItemCaretStyle() {
		SimpleAttributeSet attr = new SimpleAttributeSet();
		attr.addAttribute(StyleConstants.ColorConstants.Background, Color.MAGENTA);
		return attr;
	}
}
