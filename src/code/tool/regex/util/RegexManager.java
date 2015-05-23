package code.tool.regex.util;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import code.tool.regex.HilightListener;

public class RegexManager {
	
	private final static Character[] ESCAPE_CHARS = new Character[] {
		new Character('t'),
		new Character('b'),
		new Character('n'),
		new Character('r'),
		new Character('f'),
		new Character('\''),
		new Character('"'),
		new Character('\\'),
		new Character('.')
	};
	
	private static RegexManager mInstance;
	private Vector<HilightListener> hilight_listeners;
	
	public static RegexManager getInstance() {
		if(mInstance == null) {
			mInstance = new RegexManager();
		}
		return mInstance;
	}
	
	private RegexManager() {
		if(hilight_listeners == null) {
			hilight_listeners = new Vector<HilightListener>();
		}
	}
	
	public void addHilightListener(HilightListener listener) {
		synchronized(hilight_listeners) {
			if(hilight_listeners.contains(listener)) {
				return;
			}
			hilight_listeners.add(listener);
		}
	}
	
	public void removeHilightListener(HilightListener listener) {
		synchronized(hilight_listeners) {
			if(hilight_listeners == null) {
				return;
			}
			hilight_listeners.remove(listener);
		}
	}
	
	public String escape_character(String regex) {
		if(regex == null) {
			return null;
		}
		String regex_first = regex.replaceAll("\\\\\\\\", "\\\\");
		// System.out.println(regex_first);
		return regex_first;
	}
	
	public boolean check_escape_character_legal(String regex) {
		if(regex == null) {
			return false;
		}
		int rl = regex.length();
		for(int i = 0; i < rl; i++) {
			char ci = regex.charAt(i);
			if(ci == '\\') {
				if((i + 1 < rl) && RegexMethods.getInstance().contains(ESCAPE_CHARS, regex.charAt(i + 1)) == false) {
					return false;
				}
				else if(rl == 1) {
					return false;
				}
				i++;
			}
			else if(ci == '"') {
				if(i == 0) {
					return false;
				}
				else if((regex.charAt(i - 1) == '\\') == false) {
					return false;
				}
			}
			else if(ci == '\'') {
				if(i == 0) {
					return false;
				}
				else if((regex.charAt(i - 1) == '\\') == false) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * JDK
	 * @param sou
	 * @param regex
	 */
	public void match(String sou, String regex) {
		boolean validate = check_escape_character_legal(regex);
		if(validate == false) {
			return;
		}
		regex = escape_character(regex);
		Pattern p = null;
		try {
			p = Pattern.compile(regex);
		}
		catch(PatternSyntaxException e) {
			dispatchMatchError(e.getMessage());
		}
		if(p == null) {
			return;
		}
		Matcher m = p.matcher(sou);
		while(m.find()) {
			dispatchNewGroup(m.group(), m.start(), m.end());
		}
		dispatchMatchEnd();
	}
	
	/**
	 * JRegex
	 * @param group
	 * @param start
	 * @param end
	 */
//	public void match(String sou, String regex) {
//		boolean validate = check_escape_character_legal(regex);
//		if(validate == false) {
//			return;
//		}
//		regex = escape_character(regex);
//		Pattern p = null;
//		try {
//			p = new Pattern(regex);
//		}
//		catch(PatternSyntaxException e) {
//			dispatchMatchError(e.getMessage());
//		}
//		if(p == null) {
//			return;
//		}
//		Matcher m = p.matcher(sou);
//		while(m.find()) {
//			dispatchNewGroup(m.group(0), m.start(), m.end());
//		}
//		dispatchMatchEnd();
//	}
	
	/**
	 * ORO
	 * @param sou
	 * @param regex
	 */
//	public void match(String sou, String regex) {
//		boolean validate = check_escape_character_legal(regex);
//		if(validate == false) {
//			return;
//		}
//		regex = escape_character(regex);
//		PatternCompiler c = new Perl5Compiler();
//		Pattern p = null;
//		try {
//			p = c.compile(regex);
//		}
//		catch(MalformedPatternException e) {
//			dispatchMatchError(e.getMessage());
//		}
//		if(p == null) {
//			return;
//		}
//		PatternMatcher m = new Perl5Matcher();
//		PatternMatcherInput in = new PatternMatcherInput(sou);
//		while(m.contains(in, p)) {
//			MatchResult r = m.getMatch();
//			dispatchNewGroup(r.group(0), r.beginOffset(0), r.endOffset(0));
//		}
//		dispatchMatchEnd();
//	}
	
	public void dispatchNewGroup(String group, int start, int end) {
		synchronized(hilight_listeners) {
			int hls = hilight_listeners.size();
			for(int i = 0; i < hls; i++) {
				hilight_listeners.elementAt(i).onNewGroupMatched(group, start, end);
			}
		}
	}
	
	public void dispatchMatchEnd() {
		synchronized(hilight_listeners) {
			int hls = hilight_listeners.size();
			for(int i = 0; i < hls; i++) {
				hilight_listeners.elementAt(i).onMatchEnd();
			}
		}
	}
	
	public void dispatchMatchError(String errorMessage) {
		synchronized(hilight_listeners) {
			int hls = hilight_listeners.size();
			for(int i = 0; i < hls; i++) {
				hilight_listeners.elementAt(i).onMatchError(errorMessage);
			}
		}
	}
}
