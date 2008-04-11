package lt.tailer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import lt.wordsmith.GeneratorListener;
import lt.wordsmith.WordInfo;

import processing.core.PApplet;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;

import rita.wordnet.RiWordnet;
import rita.wordnet.WordnetError;

public class CollectionBox
{
	private static final int MAP_SIZE_THRESH = 6;
	private HashMap<String, WordInfo> _m;
	private RiWordnet _wNet;
	private GeneratorListener _listener;
	
	public CollectionBox(GeneratorListener l)
	{
		_m = new HashMap<String, WordInfo>();
		_wNet = new RiWordnet(null);
		_listener = l;
	}

	public void addLine(String line)
	{
		line = new String(line);
		line = line.toLowerCase();
		String[] chunks = line.split("[_-[\\s]+]");
		for (int i = 4; i < chunks.length; i++)
		{
			String w = chunks[i].replaceAll("[^a-zA-Z]", "");
			if (_wNet.exists(w))
			{
				String[] res = _wNet.getPos(w);
				WordInfo wi = new WordInfo(w, (res[0] != null ? changePOS(res[0]) : null));
				putWord(w, wi);
			}
			else if ((w.compareTo(" ") != 0) && (w.compareTo("") != 0))
			{
				putWord(w, new WordInfo(w, POS.NOUN));
			}
		}
	}
	
	private void putWord(String w, WordInfo wi)
	{
		_m.put(w, wi);
		
		if (_m.size() % MAP_SIZE_THRESH == 0)
		{
			_listener.generateSentence(_m.values());
		}
	}

	private POS changePOS(String string)
	{
		switch (string.toCharArray()[0])
		{
		case 'a' : 
			return POS.ADJECTIVE;
		case 'n' :
			return POS.NOUN;
		case 'r' :
			return POS.ADVERB;
		case 'v' :
			return POS.VERB;
		default :
			return null;
		}
	}

}
