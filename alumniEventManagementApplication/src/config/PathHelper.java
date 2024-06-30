package config;

import java.io.*;

public class PathHelper {
	public static FileInputStream fis=null;
	public static File f=new File(".");
	public static String path=(f.getAbsolutePath().toString()).substring(0,f.getAbsolutePath().length()-1)+"src\\";
	
	static{
		 
		String path1=path+"db.properties";
		try {
			 fis=new FileInputStream(path1);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
}


