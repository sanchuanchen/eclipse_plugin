package cn.ac.iscas.appinsighteclipse.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;

import cn.ac.iscas.appinsighteclipse.Activator;

public class NewAction implements IObjectActionDelegate {

	private Shell shell;
	
	ILog logger;
	ConsolePlugin consolePlugin;
	IConsoleManager consoleManager;
	IConsole[] consoles;
	MessageConsole appInsightConsole;
	MessageConsoleStream appInsightConsoleStream;
	
	/**
	 * Constructor for Action1.
	 */
	public NewAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		MessageDialog.openInformation(
			shell,
			"AppInsightEclipse",
			"AppInsightEclipse Instrument was executed.");
		
		
		logger = Activator.getDefault().getLog();
		logger.log(new Status(Status.INFO,Activator.PLUGIN_ID,Status.OK,
				"hi",null));
		
		
		consolePlugin = ConsolePlugin.getDefault();
		consoleManager = consolePlugin.getConsoleManager();
		consoles = consoleManager.getConsoles();
		
		logger.log(new Status(Status.INFO,Activator.PLUGIN_ID,Status.OK,
				String.valueOf(consoles.length),null));
		
		for(int i = 0;i<consoles.length;i++)
		{
			logger.log(new Status(Status.INFO,Activator.PLUGIN_ID,Status.OK,
				consoles[i].getName(),null));
			if(consoles[i].getName().equals("AppInsightConsole"))
				appInsightConsole = (MessageConsole)consoles[i];
		}
		appInsightConsole = new MessageConsole("AppInsightConsole",null);
		consoleManager.addConsoles(new IConsole[]{appInsightConsole});
		
		appInsightConsoleStream = appInsightConsole.newMessageStream();
		appInsightConsoleStream.println("hello, world");
		
		
		
		
		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
