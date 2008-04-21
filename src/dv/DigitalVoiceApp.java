package dv;

//Import the Java classes
import java.util.*;
import java.io.*;
import java.net.URL;

import dv.tailer.CollectionBox;
import dv.tailer.LogFileTailer;
import dv.tailer.LogFileTailerListener;
import dv.userinfo.UserTextHelper;
import dv.wordsmith.SentenceMaker;
import rita.RiGrammar;
import rita.RiSpeech;

import com.google.gdata.client.*;
import com.google.gdata.data.*;
import com.google.gdata.util.*;

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
	private UserTextHelper scanner;
	private int sentenceCount;
	private String post;
	private GoogleService gserv;
	private int updateCount;
	private Timer t;
	private RiSpeech rs;

	/**
	 * Creates a new Tail instance to follow the specified file
	 */
	public DigitalVoiceApp(String[] filenames)
	{
		grammar = new RiGrammar(null, GRAMMAR);
		collectionBox = new CollectionBox();
		sentenceMaker = new SentenceMaker(this, collectionBox);
		tailer = new LogFileTailer[filenames.length];
		sentenceCount = 0;
		post = new String();
		gserv = new GoogleService("blogger", "fourteen-digitalVoice-1");
		updateCount = 0;
		t = new Timer();
		//t.schedule(new GetFileTimer(), 0, 10000);

        RiSpeech.setTTSEnabled(true);
        rs = new RiSpeech(null);
        rs.setVoicePitch(60);
        rs.setVoiceRate(120);
		
		try
		{
			gserv.setUserCredentials("fourteenz.computer@gmail.com",
							"faceplz!");
		} catch (AuthenticationException e)
		{
			e.printStackTrace();
		}

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
		// System.out.println(line);
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
		// DigitalVoiceApp tail = new DigitalVoiceApp(new String[] {
		// "/var/log/syslog" , "/var/log/messages" , "/var/log/debug",
		// "/var/log/auth.log"});
		/*DigitalVoiceApp tail = new DigitalVoiceApp(new String[] {
				"/var/log/syslog" , "/var/log/messages" , "/var/log/debug", "/var/log/auth.log"
		});*/
		DigitalVoiceApp tail = new DigitalVoiceApp(new String[] {
				"/var/log/system.log", "/var/log/secure.log",
				"/var/log/fsck_hfs.log" });
	}

	public void getSentence(String sentence)
	{
		rs.speak(sentence);
		
		post = post + " " + sentence;
		sentenceCount++;
		// int n = ((int) Math.random()*6) +2;
		int n = 1;
		if (sentenceCount >= n)
		{
			updateCount++;
			String[] words = post.split(" ");
			String title = words.length >= 1 ? 
					Integer.toBinaryString(Integer.valueOf(words[0]).intValue()) : "01";
			System.out.println(title);
			try
			{
				DigitalVoiceApp.createPost(gserv, "6291120192239929914", title,
						post, "Your Computer Friend", "fourteenz.computer");
			} catch (ServiceException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			sentenceCount = 0;
			post = new String();
		}
	}

	public static Entry createPost(GoogleService myService, String blogID,
			String title, String content, String authorName, String userName)
			throws ServiceException, IOException
	{

		// Create the entry to insert
		Entry myEntry = new Entry();
		myEntry.setTitle(new PlainTextConstruct(title));
		myEntry.setContent(new PlainTextConstruct(content));
		Person author = new Person(authorName, null, userName);
		myEntry.getAuthors().add(author);

		// Ask the service to insert the new entry
		URL postUrl = new URL("http://www.blogger.com/feeds/" + blogID
				+ "/posts/default");
		return myService.insert(postUrl, myEntry);
	}

	private class GetFileTimer extends TimerTask
	{
		private UserTextHelper uth;

		public GetFileTimer()
		{
			uth = new UserTextHelper();
		}

		public void run()
		{
			getSentence(uth.getRandomFile());
		}

	}

}