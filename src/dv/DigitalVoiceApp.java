package dv;

//Import the Java classes
import java.util.*;
import java.io.*;
import dv.tailer.CollectionBox;
import dv.tailer.LogFileTailer;
import dv.tailer.LogFileTailerListener;
import dv.wordsmith.GeneratorListener;
import dv.wordsmith.SentenceMaker;
import dv.wordsmith.WordInfo;
import net.didion.jwnl.data.POS;
import rita.RiGrammar;
import com.google.gdata.data.extensions.*;

/**
 * Implements console-based log file tailing, or more specifically, tail
 * following: it is somewhat equivalent to the unix command "tail -f"
 */
public class DigitalVoiceApp implements LogFileTailerListener, SentenceListener
{
	private static final String GRAMMAR = "sentences.grammar";
	/**
	 * The log file tailer
	 */
	private LogFileTailer[] tailer;
	private CollectionBox collectionBox;
	private SentenceMaker sentenceMaker;
	private RiGrammar grammar;

	/**
	 * Creates a new Tail instance to follow the specified file
	 */
	public DigitalVoiceApp(String[] filenames)
	{
		grammar = new RiGrammar(null, GRAMMAR);
		collectionBox = new CollectionBox();
		sentenceMaker = new SentenceMaker(this, collectionBox);
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
		collectionBox.addLine(line);
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
		DigitalVoiceApp tail = new DigitalVoiceApp(new String[] { "/var/log/syslog" , "/var/log/messages" , "/var/log/debug", "/var/log/auth.log"});
	}

	public void getSentence(String sentence){
		
	}
	public static Entry createPost(
		    GoogleService myService, String blogID, String title,
		    String content, String authorName, String userName)
		    throws ServiceException, IOException {
		  // Create the entry to insert
		  Entry myEntry = new Entry();
		  myEntry.setTitle(new PlainTextConstruct(title));
		  myEntry.setContent(new PlainTextConstruct(content));
		  Person author = new Person(authorName, null, userName);
		  myEntry.getAuthors().add(author);

		  // Ask the service to insert the new entry
		  URL postUrl = new URL("http://www.blogger.com/feeds/" + blogID + "/posts/default");
		  return myService.insert(postUrl, myEntry);
		}

	
}