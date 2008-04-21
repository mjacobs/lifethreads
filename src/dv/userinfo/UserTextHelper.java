package dv.userinfo;

import java.io.File;

public class UserTextHelper
{
	public UserTextHelper()
	{
		
	}
	
	public String getRandomFile()
	{
		File uDir = new File(System.getProperty("user.dir"));
		File[] files = uDir.listFiles();
		return null;
	}
}
