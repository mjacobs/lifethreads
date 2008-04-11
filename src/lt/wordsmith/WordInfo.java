package lt.wordsmith;

import net.didion.jwnl.data.POS;

public class WordInfo
{
	public String word;
	public POS pos;
	public int count;
	public WordInfo(String w, POS p)
	{
		word = w;
		pos = p;
	}
}