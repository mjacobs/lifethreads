package dv.userinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.apache.poi.hwpf.extractor.WordExtractor;


public class UserTextHelper
{
	public static double REJECTION_SAMPLE = .05;
	
	private WordExtractor we;
	private Random r;
	
	public UserTextHelper()
	{
		r = new Random();
	}
	
	public String getRandomFile()
	{
		File[] files;
		File d;

		File uDir = new File(System.getProperty("user.home"));
		System.out.println(System.getProperty("user.home"));
		Queue<File> q = new LinkedList<File>();
		q.add(uDir);
		
		while (!q.isEmpty())
		{
			d = q.remove();
			files = d.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].canRead() && files[i].isFile())
				{
					if (files[i].getName().endsWith(".txt"))
					{
						if (isAccepted())
						{
							try
							{
								BufferedReader in = new BufferedReader(
										new FileReader(files[i]));
								String str;
								String ret = "";
								while ((str = in.readLine()) != null)
								{
									ret += str + " ";
								}
								in.close();
								return ret;
							} 
							catch (FileNotFoundException e)
							{
								e.printStackTrace();
							} 
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					else if (files[i].getName().endsWith(".doc"))
					{
						/*try
						{
							we = new WordExtractor(new FileInputStream(files[i]));
						} catch (FileNotFoundException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return we.getText();*/
					}
				} 
				else if (files[i].isDirectory())
				{
					q.add(files[i]);
				}
			}
		}
		return null;
	}
	private boolean isAccepted()
	{
		return (Math.abs(r.nextDouble()) < REJECTION_SAMPLE) ? true : false;
	}
	
	public static void main(String[] w)
	{
		UserTextHelper u = new UserTextHelper();
		System.out.println(u.getRandomFile());
	}
}
