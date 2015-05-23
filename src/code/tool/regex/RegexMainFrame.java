package code.tool.regex;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import code.tool.regex.data.GroupManager;
import code.tool.regex.data.ResultGroup;
import code.tool.regex.style.RegexPaneStyle;
import code.tool.regex.style.SourceMatchStyle;
import code.tool.regex.style.StyleManager;
import code.tool.regex.util.RegexManager;
import code.tool.regex.util.TextPaneManager;

public class RegexMainFrame implements HilightListener, ActionListener, CaretListener {
	
	private BoxLayout boxLayout;
	private Container contentPanel;
	private FlowLayout sourceHintFlowLayout;
	private FlowLayout regexFlowLayout;
	private FlowLayout resultFlowLayout;
	private JButton doMatchButton;
	private JFrame frame;
	private JLabel regexHintLabel;
	private JLabel resultShowLabel;
	private JLabel sourceHintLabel;
	private JPanel sourceHintPanel;
	private JPanel regexPanel;
	private JPanel resultPanel;
	private JScrollPane sourceScrollPane;
	private JScrollPane regexScrollPane;
	private JScrollPane resultScrollPane;
	private JTextPane regexPane;
	private JTextPane resultPane;
	private JTextPane sourcePane;
	
	private int totalMatch;
	
	private boolean matchDone;
	
	public RegexMainFrame() {
		this.frame = new JFrame("Java Regex Tester(Free And Open Source)");
		this.contentPanel = this.frame.getContentPane();
		this.boxLayout = new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS);
		this.contentPanel.setLayout(boxLayout);
		
		this.sourceHintPanel = new JPanel();
		this.sourceHintFlowLayout = new FlowLayout(FlowLayout.LEFT);
		this.sourceHintPanel.setLayout(this.sourceHintFlowLayout);
		this.sourceHintLabel = new JLabel("Enter the text you want to use as source in blow text area:");
		this.sourceHintLabel.setForeground(Color.BLUE);
		this.sourceHintPanel.add(this.sourceHintLabel);
		this.sourceHintPanel.setBackground(Color.CYAN);
		this.contentPanel.add(this.sourceHintPanel);
		
		this.sourcePane = new JTextPane(new SourceMatchStyle());
		this.sourcePane.setPreferredSize(new Dimension(700, 300));
		this.sourceScrollPane = new JScrollPane(this.sourcePane);
		this.sourceScrollPane.setPreferredSize(this.sourcePane.getPreferredSize());
		this.contentPanel.add(this.sourceScrollPane);
		
		this.regexPanel = new JPanel();
		this.regexFlowLayout = new FlowLayout(FlowLayout.LEFT);
		this.regexPanel.setLayout(regexFlowLayout);
		this.regexHintLabel = new JLabel("Enter the regex you want to test in blow text area:");
		this.regexHintLabel.setForeground(Color.BLUE);
		this.regexPanel.add(this.regexHintLabel);
		this.regexPanel.setBackground(Color.CYAN);
		this.contentPanel.add(this.regexPanel);
		
		this.regexPane = new JTextPane(new RegexPaneStyle());
		this.regexPane.setPreferredSize(new Dimension(700, 120));
		this.regexScrollPane = new JScrollPane(this.regexPane);
		this.regexScrollPane.setPreferredSize(this.regexPane.getPreferredSize());
		this.contentPanel.add(this.regexScrollPane);
		
		this.resultPanel = new JPanel();
		this.resultFlowLayout = new FlowLayout(FlowLayout.LEFT);
		this.resultPanel.setLayout(resultFlowLayout);
		this.doMatchButton = new JButton("Test the regex now.");
		this.doMatchButton.setForeground(Color.MAGENTA);
		this.doMatchButton.setActionCommand("match");
		this.doMatchButton.addActionListener(this);
		this.resultPanel.add(this.doMatchButton);
		this.totalMatch = 0;
		this.resultShowLabel = new JLabel("total match " + this.totalMatch + " items and result is below:");
		this.resultShowLabel.setForeground(Color.BLUE);
		this.resultPanel.add(resultShowLabel);
		this.resultPanel.setBackground(Color.CYAN);
		this.contentPanel.add(this.resultPanel);
		
		this.resultPane = new JTextPane();
		this.resultPane.setPreferredSize(new Dimension(700, 120));
		this.resultPane.addCaretListener(this);
		this.resultScrollPane = new JScrollPane(this.resultPane);
		this.resultScrollPane.setPreferredSize(this.resultPane.getPreferredSize());
		this.contentPanel.add(this.resultScrollPane);
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		
		RegexManager.getInstance().addHilightListener(this);
		this.matchDone = false;
	}

	@Override
	public void onNewGroupMatched(String group, int start, int end) {
		// TODO Auto-generated method stub
		// System.out.println("onNewGroupMatched, got new match group == " + group + "and start ==" + start + " end == " + end);
		this.totalMatch++;
		this.resultShowLabel.setText("total match " + this.totalMatch + " items and result is below.");
		this.sourcePane.getStyledDocument().setCharacterAttributes(start, end - start, StyleManager.getInstance().getMatchHilightItemStyle(), true);
		GroupManager.getInstance().addGroup(start, end, 1);
		TextPaneManager.getInstance().append(this.resultPane, group, true);
	}
	
	@Override
	public void onMatchEnd() {
		// TODO Auto-generated method stub
		// System.out.println("onMatchEnd, match end");
		this.resultShowLabel.setText("total match " + this.totalMatch + " items and result is below.");
		this.doMatchButton.setEnabled(true);
		this.matchDone = true;
	}
	
	@Override
	public void onMatchError(String errorMessage) {
		// TODO Auto-generated method stub
		this.resultPane.setText(errorMessage);
		StyleManager.getInstance().attributeString(this.resultPane.getStyledDocument(), 0, this.resultPane.getStyledDocument().getLength(), StyleManager.getInstance().getRegexWrongStyle());
		this.doMatchButton.setEnabled(true);
		this.totalMatch = 0;
		this.matchDone = false;
		this.resultShowLabel.setText("total match " + this.totalMatch + " items and result is below.");
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		// TODO Auto-generated method stub
		String actionCommand = actionEvent.getActionCommand();
		if(actionCommand.equals("match")) {
			String source = this.sourcePane.getText();
			if(source == null || source.equals("")) {
				return;
			}
			TextPaneManager.getInstance().replaceAllEnterToWrap(this.sourcePane);
			source = this.sourcePane.getText();
			if(source == null || source.equals("")) {
				return;
			}
			
			String regex = this.regexPane.getText();
			if(regex == null || regex.equals("")) {
				return;
			}
			// System.out.println("actionPerformed, show what regex you want to test == " + regex);
			this.doMatchButton.setEnabled(false);
			this.totalMatch = 0;
			StyleManager.getInstance().resetPane(this.sourcePane.getStyledDocument());
			
			TextPaneManager.getInstance().clear(this.resultPane);
			// System.out.println(RegexManager.getInstance().check_escape_character_legal(regex));
			GroupManager.getInstance().resetGroups();
			RegexManager.getInstance().match(source, regex);
		}
	}

	@Override
	public void caretUpdate(CaretEvent caretEvent) {
		// TODO Auto-generated method stub
		if(this.matchDone == false) {
			return;
		}
		int position = caretEvent.getDot();
		// System.out.println(position);
		// System.out.println(GroupManager.getInstance().detectWithin(position));
		ResultGroup caretGroup = GroupManager.getInstance().detectWithin(position);
		if(caretGroup == null) {
			System.out.println(position + " got null Group to handle.");
			return;
		}
		((SourceMatchStyle) this.sourcePane.getStyledDocument()).hilightCaretGroup(caretGroup);
		try {
			this.sourcePane.scrollRectToVisible(this.sourcePane.modelToView(caretGroup.getStart()));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
