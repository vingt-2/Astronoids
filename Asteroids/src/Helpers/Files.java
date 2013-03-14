package Helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Files 
{
	public final static int EOF = -1;
	public final static String BROKEN_STRING = "BROKEN_STRING";
	
	public static String ReadFile(String pathToFile)
	{
		String outputString = "";

		FileReader file;
		try 
		{
			file = new FileReader(pathToFile);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Cannot read: \""+ pathToFile + " \"");
			return BROKEN_STRING;
		}
		int charPtr = 0;
		while(charPtr != EOF)
		{
			if(charPtr != 0)
			{
				outputString += (char)charPtr;
			}
			try 
			{
				charPtr = file.read();
			} 
			catch (IOException e) 
			{
				System.out.println("Something wrong while buffering \""+ pathToFile + " \"");
				return BROKEN_STRING;
			}
		}
		return outputString;
	}
}
