package lt.tailer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lt.wordsmith.WordInfo;

import net.didion.jwnl.data.POS;
import rita.wordnet.RiWordnet;

public class CollectionBox
{
	private static final int MAP_SIZE_THRESH = 4;
	private HashMap<POS, Set<String>> _ms;
	private HashMap<String, WordInfo> _m;
	private RiWordnet _wNet;
	
	public CollectionBox()
	{
		_ms = new HashMap<POS, Set<String>>();
		_m = new HashMap<String, WordInfo>();
		_wNet = new RiWordnet(null);
	}

	public void addLine(String line)
	{
		//System.out.println(line);
		line = new String(line);
		line = line.toLowerCase();
		String[] chunks = line.split("[_-[\\s]+]");
		for (int i = 4; i < chunks.length; i++)
		{
			String w = chunks[i].replaceAll("[^a-zA-Z]", "");
			if ((w.length() > 3) && (_wNet.exists(w)))
			{
				String[] res = _wNet.getPos(w);
				POS p = null;
				if ((changePOS(res[0]) == POS.NOUN) && (res.length > 1))
				{
					p = changePOS(res[1]);
				}
				else
				{
					p = (res[0] != null ? changePOS(res[0]) : null);
				}
				putWord(w, p);
			}
		}
	}
	
	private void putWord(String w, POS p)
	{
		Set<String> s = _ms.get(p);
		if (s == null)
		{
			s = new HashSet<String>();
			_ms.put(p, s);
		}
		
		s.add(w);
		
		String sizes = "Sizes: ";
		for (Iterator<Set<String>> it = _ms.values().iterator(); it.hasNext(); )
		{
			Set<String> stmp = it.next();
			sizes += stmp.size() + ", ";
			Object[] ss = stmp.toArray();
			String vals = "";
			for (int i = 0; i < ss.length; i++)
				vals += (String)ss[i] + ", ";
			//System.out.println(vals);
		}
		//System.out.println(sizes);
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

	public boolean isReady()
	{
		int cnt = 0;
		for (Iterator<Set<String>> it = _ms.values().iterator(); it.hasNext(); )
		{
			cnt++;
			Set<String> s = it.next();
			if (s.size() < MAP_SIZE_THRESH)
				return false;
		}
		return (cnt == 4) ? true : false;
	}

	public HashMap<POS, Set<String>> getWordMap()
	{
		HashMap<POS, Set<String>> tmp = _ms;
		return tmp;
	}

}
