package lt.tailer;

import java.util.HashMap;
import java.util.Iterator;
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
		String[] chunks = line.split("\\s+");
		for (int i = 4; i < chunks.length; i++)
		{
			
			WordInfo w = new WordInfo();
			w.word = chunks[i].replaceAll("[^a-zA-Z]","");
			w.pos = null;
			if (w.pos != POS.NONE)
				_m.put(chunks[i], w);
		}
		
		String ret = "";
		for (Iterator<String> it = _m.keySet().iterator(); it.hasNext(); )
			ret += it.next() + ", ";
		System.out.println("Added " + ret);
	}
	
	public void addWord(String word)
	{
		
	}
	
	public enum POS { NOUN, VERB, ADJ, NONE };
	
	private class WordInfo
	{
		public String word;
		public POS pos;
		public int count;
	}
}
