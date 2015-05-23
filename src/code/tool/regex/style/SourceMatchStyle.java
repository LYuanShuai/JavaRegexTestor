package code.tool.regex.style;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;

import code.tool.regex.data.ResultGroup;

public class SourceMatchStyle extends DefaultStyledDocument {
	
	private static final long serialVersionUID = -2304494608851779576L;
	
	private ResultGroup mLastHighLightGroup;
	
	public void hilightMatchString(int pos, int length) {
		StyleManager.getInstance().attributeString(this, pos, length - pos, StyleManager.getInstance().getMatchHilightItemStyle());
	}
	
	public void hilightCaretGroup(ResultGroup caretGroup) {
		if((this.mLastHighLightGroup == null) == false) {
			hilightGroup(this.mLastHighLightGroup, StyleManager.getInstance().getMatchHilightItemStyle());
		}
		this.mLastHighLightGroup = caretGroup;
		hilightGroup(caretGroup, StyleManager.getInstance().getMatchItemCaretStyle());
	}
	
	public void hilightMatchGroup(ResultGroup matchGroup) {
		hilightGroup(matchGroup, StyleManager.getInstance().getMatchHilightItemStyle());
	}
	
	public void hilightGroup(ResultGroup group, AttributeSet attr) {
		this.setCharacterAttributes(group.getStart(), group.getLength(), attr, true);
	}
	
	@Override
	protected void insertUpdate(AbstractDocument.DefaultDocumentEvent chng, AttributeSet attr) {
		// StyleManager.getInstance().resetPane(this);
		super.insertUpdate(chng, attr);
	}
}
