package lt.test;

//Import the Java classes
import java.util.*;
import java.io.*;
import net.didion.jwnl.data.POS;

import rita.RiGrammar;

import lt.tailer.CollectionBox;
import lt.tailer.LogFileTailer;
import lt.tailer.LogFileTailerListener;
import lt.wordsmith.GeneratorListener;
import lt.wordsmith.SentenceMaker;
import lt.wordsmith.WordInfo;

/**
 * Implements console-based log file tailing, or more specifically, tail
 * following: it is somewhat equivalent to the unix command "tail -f"
 */
public class Tail implements LogFileTailerListener
{
	private static final String GRAMMAR = "sentences.grammar";
	/**
	 * The log file tailer
	 */
	private LogFileTailer[] tailer;
	private CollectionBox cb;
	private SentenceMaker sm;
	private RiGrammar g;

	/**
	 * Creates a new Tail instance to follow the specified file
	 */
	public Tail(String[] filenames)
	{
		g = new RiGrammar(null, GRAMMAR);
		cb = new CollectionBox();
		sm = new SentenceMaker(cb);
		tailer = new LogFileTailer[filenames.length];
		
		for (int i = 0; i < filenames.length; i++)
		{
			tailer[i] = new LogFileTailer(new File(filenames[i]), 1000, false);
			tailer[i].addLogFileTailerListener(this);
			tailer[i].start();
		}
	}

	/**
	 * A new line has been added to the tailed log file
	 * 
	 * @param line
	 *            The new line that has been added to the tailed log file
	 */
	public void newLogFileLine(String line)
	{
		cb.addLine(line);
		//System.out.println(line);
	}

	/**
	 * Command-line launcher
	 */
	public static void main(String[] args)
	{
		if (args.length < 0)
		{
			System.out.println("Usage: Tail <filename>");
			System.exit(0);
		}
		Tail tail = new Tail(new String[] { "/var/log/syslog" , "/var/log/messages" , "/var/log/debug", "/var/log/auth.log"});
	}

}