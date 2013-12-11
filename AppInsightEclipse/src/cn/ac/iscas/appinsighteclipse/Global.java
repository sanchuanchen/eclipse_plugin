package cn.ac.iscas.appinsighteclipse;

import org.eclipse.core.runtime.ILog;
import org.eclipse.ui.console.MessageConsoleStream;

public class Global {
	
	static MessageConsoleStream messageConsoleStream;
	static ILog logger;

	public static MessageConsoleStream getMessageConsoleStream() {
		return messageConsoleStream;
	}

	public static void setMessageConsoleStream(
			MessageConsoleStream messageConsoleStream) {
		Global.messageConsoleStream = messageConsoleStream;
	}

	public static ILog getLogger() {
		return logger;
	}

	public static void setLogger(ILog logger) {
		Global.logger = logger;
	}
	
	
	
}
