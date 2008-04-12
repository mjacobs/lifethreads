package lt.wordsmith;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import lt.tailer.CollectionBox;
import net.didion.jwnl.data.POS;
import rita.RiGrammar;

public class SentenceMaker implements GeneratorListener
{
	private RiGrammar g;
	private Random _rand;
	private CollectionBox _cb;
	private static final String GRAMMAR = "sentences.grammar";
	
	public SentenceMaker(CollectionBox cb)
	{
		_rand = new Random();
		g = new RiGrammar(null, GRAMMAR);
		_cb = cb;
		
		Timer t = new Timer();
		t.schedule(new CollectorChecker(), 1000);
	}
	
	public void generateSentence(HashMap<POS, Set<String>> _ms)
	{
		String exp = g.expand();
		exp = exp.replaceAll(" xx", "");
		
		String[] wordsN = _ms.get(POS.NOUN).toArray(new String[] {});		
		while (exp.indexOf("xnx") >= 0)
			exp = exp.replaceFirst("xnx", wordsN[_rand.nextInt(wordsN.length)]);
		
		String[] wordsV = _ms.get(POS.VERB).toArray(new String[] {});		
		while (exp.indexOf("xvx") >= 0)
			exp = exp.replaceFirst("xvx", wordsV[_rand.nextInt(wordsV.length)]);
		
		String[] wordsA = _ms.get(POS.ADJECTIVE).toArray(new String[] {});		
		while (exp.indexOf("xadjx") >= 0)
			exp = exp.replaceFirst("xadjx", wordsA[_rand.nextInt(wordsA.length)]);

		System.out.println(exp);
	}
	
	private class CollectorChecker extends TimerTask
	{
		public void run()
		{
			if (_cb.isReady())
			{
				generateSentence(_cb.getWordMap());
			}
		}	
	}
}
