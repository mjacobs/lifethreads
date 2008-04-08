package lt.tailer;

import java.util.HashMap;
import java.util.regex.Pattern;

import sun.misc.Regexp;

public class CollectionBox
{
	private HashMap<String, WordInfo> _m;
	
	public CollectionBox()
	{
		_m = new HashMap<String, WordInfo>();
	}
	
	public void addLine(String line)
	{
		line = line.toLowerCase();
		Pattern p = Pattern.compile("a*b");
		String[] chunks = line.split(" ");
		for (int i = 4; i < chunks.length; i++)
		{
			
		}
	}
	
	public void addWord(String word)
	{
		
	}
	
	public enum POS { NOUN, VERB, ADJ };
	
	private class WordInfo
	{
		public String word;
		public POS pos;
		public int count;
	}
}
