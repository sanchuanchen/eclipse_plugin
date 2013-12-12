package cn.ac.iscas.appinsighteclipse.popup.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

import cn.ac.iscas.appinsighteclipse.Activator;
import cn.ac.iscas.appinsighteclipse.Global;
import cn.ac.iscas.appinsighteclipse.util.AppInsightEclipseUtil;

/**
 * @author sanchuan
 *
 */
public class ClickAppInstruAction implements IObjectActionDelegate {

	Shell shell;
	IWorkbenchPart iWorkbenchPart;
	
	
	ILog logger;
	ConsolePlugin consolePlugin;
	IConsoleManager consoleManager;
	IConsole[] consoles;
	MessageConsole appInsightConsole;
	MessageConsoleStream appInsightConsoleStream;
	IWorkbenchPage workbenchPage;
	String iConsoleViewId;
	IConsoleView iConsoleView;
	TreePath[] treePath;
	
	ISelection iSelection;
	Object element;
	IJavaProject iJavaProject;
	IPath iPath;
	IProject iProject;
	
	Bundle bundle;
	URL pluginURL, pluginFileURL;
	File hiFile;
	
	/**
	 * Constructor for Action1.
	 */
	public ClickAppInstruAction() {
		super();
	}
	
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		iWorkbenchPart = targetPart;
		workbenchPage = targetPart.getSite().getPage();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		showMessageBox();
		setLogger();
		logInfoMessage("hi");
		setApplicationConsole();
		setApplicationConsoleStream();
		revealApplicationConsole();
		copyIntoWProjectSourceFolder();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * show message box
	 * 
	 */
	void showMessageBox()
	{
		MessageDialog.openInformation(
				shell,
				"AppInsightEclipse",
				"AppInsightEclipse Instrument was executed.");
	}
	
	/**
	 * set logger
	 * 
	 */
	void setLogger()
	{
		logger = Activator.getDefault().getLog();
		Global.setLogger(logger);
	}
	
	/**
	 * log info message to IDE console
	 */
	void logInfoMessage(String m)
	{
		logger.log(new Status(Status.INFO,Activator.PLUGIN_ID,Status.OK,
				m,null));
	}
	
	/**
	 * set eclipse application output console
	 */
	void setApplicationConsole()
	{
		consolePlugin = ConsolePlugin.getDefault();
		consoleManager = consolePlugin.getConsoleManager();
		consoles = consoleManager.getConsoles();
		
		logInfoMessage(String.valueOf(consoles.length));
		
		for(int i = 0;i<consoles.length;i++)
		{
			logger.log(new Status(Status.INFO,Activator.PLUGIN_ID,Status.OK,
				consoles[i].getName(),null));
			if(consoles[i].getName().equals("AppInsightConsole"))
				appInsightConsole = (MessageConsole)consoles[i];
		}
		appInsightConsole = new MessageConsole("AppInsightConsole",null);
		consoleManager.addConsoles(new IConsole[]{appInsightConsole});
	}
	
	/**
	 * set application output stream
	 */
	void setApplicationConsoleStream()
	{
		appInsightConsoleStream = appInsightConsole.newMessageStream();
		Global.setMessageConsoleStream(appInsightConsoleStream);
	}
	
	/**
	 * application console print line
	 */
	void applicationConsolePrintln(String m)
	{
		appInsightConsoleStream.println(m);
	}
	
	/**
	 * reveal and show application console
	 */
	void revealApplicationConsole()
	{
		iConsoleViewId = IConsoleConstants.ID_CONSOLE_VIEW;
		try {
			iConsoleView = (IConsoleView)workbenchPage.showView(iConsoleViewId);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		iConsoleView.display(appInsightConsole);
	}	

	/**
	 * copy source library files into application workspace project
	 */
	void copyIntoWProjectSourceFolder()
	{
		applicationConsolePrintln("entering copy function");
		//get selection
		iSelection = iWorkbenchPart.getSite().getSelectionProvider().getSelection();
		applicationConsolePrintln(iSelection.toString());
		element = ((IStructuredSelection)iSelection).getFirstElement();
		logInfoMessage(element.getClass().toString());
		
		//if selection project is a java project
		if(element instanceof IJavaProject)
		{
			//get application project local path
			iJavaProject = (IJavaProject)element;
			iPath = iJavaProject.getPath();
			
			logInfoMessage("application project local path " + iPath.toString());
			applicationConsolePrintln("application project local path " + iPath.toString());
			
			//get application project full path
			iProject = iJavaProject.getProject();
			iPath = iProject.getLocation();
			
			logInfoMessage("application project full path " + iPath.toString());
			applicationConsolePrintln("application project full path " + iPath.toString());
			//output full path
			String outputPath = iPath.toString() + "/src/";
			logInfoMessage("output full path " + outputPath);
			applicationConsolePrintln("output full path " + outputPath);
			/*			
			File inputFile = new File("dir" + File.separator +"hello.txt");
			logInfoMessage(inputFile.getAbsolutePath());
			
			try {
				if (!inputFile.exists()) {
					if (!inputFile.getParentFile().exists())
						inputFile.getParentFile().mkdirs();
					inputFile.createNewFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			
			/*
			File outputFile = new File(iPath.toString() + File.separator +"lib"
					+ File.separator + "hello.txt");
			
			logInfoMessage(outputFile.getAbsolutePath());
			if (outputFile.exists())
				outputFile.delete();
			if (!outputFile.getParentFile().exists())
				outputFile.getParentFile().mkdirs();
			*/
			
			/*
			AppInsightEclipseUtil.copyFile(inputFile, outputFile);
			*/
			
			/*
			InputStream in = cn.ac.iscas.appinsighteclipse.Activator.class
					.getResourceAsStream("/dir/hi.txt");
			
			URL url = cn.ac.iscas.appinsighteclipse.Activator.class.getResource("");
			
			logInfoMessage("url is " + url);
			
			FileInputStream fileInputStream = (FileInputStream)in;
			
			logInfoMessage(fileInputStream.toString());		
			
			AppInsightEclipseUtil.copyFile(fileInputStream, outputFile);

			File file = new File("./dir/my.txt");
			try {
				if(file.exists())
					file.delete();
				if(!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				file.createNewFile();
				logInfoMessage("file created in" + file.getAbsolutePath());
			} catch(IOException e) {
				e.printStackTrace();
			}
			*/
			
			/*
			String bundlePath = Platform.getInstanceLocation().getURL().getPath();
			logInfoMessage("bundle full path " + bundlePath);
			
			String inputPath = bundlePath + "dir/";
			logInfoMessage("input full path" + inputPath);
			*/
			
			/*
			String inputPath = "/dir/";
			
			AppInsightEclipseUtil.copyFilesToSourceFolder(inputPath,outputPath);
			
			applicationConsolePrintln("after function call");
			
			String s = cn.ac.iscas.appinsighteclipse.Activator.class.getResource("/dir/").toString();
			
			logInfoMessage(s);
			applicationConsolePrintln(s);
			
			*/
			
			applicationConsolePrintln("start to get bundle URL");
			bundle = Platform.getBundle("cn.ac.iscas.appinsighteclipse");
			pluginURL = Platform.find(bundle, new Path(""));
			applicationConsolePrintln("plugin URL " + pluginURL);
			applicationConsolePrintln("plugin URL " + pluginURL);
			try {
				pluginFileURL = Platform.asLocalURL(pluginURL);
				String pluginPath = pluginFileURL.getPath();
				logInfoMessage("plugin path " + pluginPath);
				applicationConsolePrintln("plugin path " + pluginPath);
				hiFile = new File(pluginPath + "dir/hi.txt");
				String sourcePath = pluginPath + "dir/";
				applicationConsolePrintln(sourcePath);
				
				/*
				if(hiFile.exists())
				{
					applicationConsolePrintln("file exists ");
					AppInsightEclipseUtil.displayFile(hiFile);
					

					
				}
				else
					applicationConsolePrintln("file does not exist ");
				*/
				
				
				//copy appsight sdk into application workspace source code folder
				applicationConsolePrintln(sourcePath);
				applicationConsolePrintln(outputPath);
				AppInsightEclipseUtil.copyFilesToSourceFolder(sourcePath, outputPath);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//TBD
			
		}
		else
		{
			applicationConsolePrintln("not a java project!");
		}
	}
	
	
}
