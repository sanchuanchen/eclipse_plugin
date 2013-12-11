package cn.ac.iscas.appinsighteclipse.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.ui.console.MessageConsoleStream;

import cn.ac.iscas.appinsighteclipse.Globe;


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
			
			
			bufferedOutputStream.flush();
			
			bufferedInputStream.close();
			fileInputStream.close();
			bufferedOutputStream.close();
			fileOutputStream.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * copy file from fileOutputStream to targetFile
	 * @param fileInputStream
	 * @param targetFile
	 */
	public static void copyFile(FileInputStream fileInputStream, File targetFile)
	{
		try{
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
			
			FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			
			byte[] b = new byte[1024*10];
			int len;
			
			while((len = bufferedInputStream.read(b))!= -1)
			{
				bufferedOutputStream.write(b,0,len);
			}
			
			bufferedOutputStream.flush();
			
			bufferedInputStream.close();
			fileInputStream.close();
			bufferedOutputStream.close();
			fileOutputStream.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}	
	
	/**
	 * display file content from fileOutputStream
	 * @param fileInputStream
	 */
	public static void displayFile(File file)
	{
		try{
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
			MessageConsoleStream messageConsoleStream = Globe.getMessageConsoleStream();
			
			byte[] b = new byte[1024*10];
			int len;
			
			while((len = bufferedInputStream.read(b))!= -1)
			{
				messageConsoleStream.write(b,0,len);
			}
			
			messageConsoleStream.flush();
			
			bufferedInputStream.close();
			fileInputStream.close();
			messageConsoleStream.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}	
	
	
	
	
	/**
	 * copy all files in the input folder to target folder
	 * @param inputPath
	 * note that the inputPath should be a local path to the plug-in bundle
	 * @param outputPath
	 */
	public static void copyFilesToSourceFolder(String inputPath,String outputPath)
	{
		
	}
}
