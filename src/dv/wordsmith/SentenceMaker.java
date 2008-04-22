package dv.wordsmith;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import dv.SentenceListener;
import dv.tailer.CollectionBox;
import net.didion.jwnl.data.POS;
import rita.RiGrammar;
import rita.RiSpeech;

public class SentenceMaker implements GeneratorListener
{
	private RiGrammar g;
	private Random _rand;
	private SentenceListener _l;
	private CollectionBox _cb;
	private static final String GRAMMAR = "sentences.grammar";
	
	public SentenceMaker(SentenceListener listener, CollectionBox cb)
	{
		_rand = new Random();
		g = new RiGrammar(null, GRAMMAR);
		_cb = cb;
		_l = listener;

		Timer t = new Timer();
		t.schedule(new CollectorChecker(), 1000,1000);
	}
	
	public void generateSentence(HashMap<POS, Set<String>> _ms)
	{
		String exp = g.expand();
		exp = exp.replaceAll(" xx", "");
		
		String[] wordsN = _ms.get(POS.NOUN).toArray(new String[] {});		
		while (exp.indexOf("xnounx") >= 0)
		{
			String word = wordsN[_rand.nextInt(wordsN.length)];
			_ms.get(POS.NOUN).remove(word);
			exp = exp.replaceFirst("xnounx", word);
		}
		
		String[] wordsV = _ms.get(POS.VERB).toArray(new String[] {});		
		while (exp.indexOf("xverbx") >= 0)
		{
			String word = wordsV[_rand.nextInt(wordsV.length)];
			_ms.get(POS.VERB).remove(word);
			exp = exp.replaceFirst("xverbx", word);
		}
		
		String[] wordsA = _ms.get(POS.ADJECTIVE).toArray(new String[] {});		
		while (exp.indexOf("xadjectivex") >= 0)
		{
			String word = wordsA[_rand.nextInt(wordsA.length)];
			_ms.get(POS.ADJECTIVE).remove(word);
			exp = exp.replaceFirst("xadjectivex", word);
		}
		
		String[] wordsADV = _ms.get(POS.ADVERB).toArray(new String[] {});		
		while (exp.indexOf("xadverbx") >= 0)
		{
			String word = wordsADV[_rand.nextInt(wordsADV.length)];
			_ms.get(POS.ADVERB).remove(word);
			exp = exp.replaceFirst("xadverbx", word);
		}
		String first = exp.substring(0,1).toUpperCase();
		exp = first + exp.substring(1);
		_l.getSentence(exp+".");
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
