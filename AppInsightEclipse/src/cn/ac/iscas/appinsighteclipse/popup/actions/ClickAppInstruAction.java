package cn.ac.iscas.appinsighteclipse.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
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
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;

import cn.ac.iscas.appinsighteclipse.Activator;;

/**
 * @author sanchuan
 *
 */
public class ClickAppInstruAction implements IObjectActionDelegate {

	private Shell shell;
	
	ILog logger;
	ConsolePlugin consolePlugin;
	IConsoleManager consoleManager;
	IConsole[] consoles;
	MessageConsole appInsightConsole;
	MessageConsoleStream appInsightConsoleStream;
	IWorkbenchPage workbenchPage;
	String iConsoleViewId;
	IConsoleView iConsoleView;
	
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
		applicationConsolePrintln("hello, world");
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
}
