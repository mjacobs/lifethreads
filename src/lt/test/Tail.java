package lt.test;

//Import the Java classes
import java.util.*;
import java.io.*;

import lt.tailer.CollectionBox;
import lt.tailer.LogFileTailer;
import lt.tailer.LogFileTailerListener;
import lt.wordsmith.GeneratorListener;
import lt.wordsmith.WordInfo;

/**
 * Implements console-based log file tailing, or more specifically, tail
 * following: it is somewhat equivalent to the unix command "tail -f"
 */
public class Tail implements LogFileTailerListener, GeneratorListener
{
	/**
	 * The log file tailer
	 */
	private LogFileTailer tailer;
	private CollectionBox cb;
	

	/**
	 * Creates a new Tail instance to follow the specified file
	 */
	public Tail(String filename)
	{
		cb = new CollectionBox(this);
		tailer = new LogFileTailer(new File(filename), 1000, false);
		tailer.addLogFileTailerListener(this);
		tailer.start();
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
		System.out.println(line);
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
		Tail tail = new Tail("/var/log/system.log");//(args[0]);
	}

	public void generateSentence(Collection<WordInfo> c)
	{
		for (Iterator<WordInfo> it = c.iterator(); it.hasNext(); )
		{
			WordInfo w = it.next();
			System.out.print(w.word + "(" + w.pos.getLabel() + "), ");
		}
		System.out.print("\n");
	}
}