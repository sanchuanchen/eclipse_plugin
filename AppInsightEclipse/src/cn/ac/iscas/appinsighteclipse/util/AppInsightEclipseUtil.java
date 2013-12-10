package cn.ac.iscas.appinsighteclipse.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * AppInsightEclipse utility fuctions
 * @author sanchuan
 *
 */
public class AppInsightEclipseUtil {

	
	/**
	 * copy file from sourceFile to targetFile
	 * @param sourceFile
	 * @param targetFile
	 */
	public static void copyFile(File sourceFile, File targetFile)
	{
		try{
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
			
			FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			
			byte[] b = new byte[1024*10];
			int len;
			
			while((len = bufferedInputStream.read(b))!= -1)
			{
				bufferedOutputStream.write(b,0,len);
			}
			
			bufferedInputStream.close();
			fileInputStream.close();
			bufferedOutputStream.flush();
			fileOutputStream.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
}
