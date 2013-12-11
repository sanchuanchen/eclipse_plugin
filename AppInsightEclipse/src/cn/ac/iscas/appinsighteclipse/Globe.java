package cn.ac.iscas.appinsighteclipse;

import org.eclipse.ui.console.MessageConsoleStream;

public class Globe {
	
	public static MessageConsoleStream messageConsoleStream;

	public static MessageConsoleStream getMessageConsoleStream() {
		return messageConsoleStream;
	}

	public static void setMessageConsoleStream(
			MessageConsoleStream messageConsoleStream) {
		Globe.messageConsoleStream = messageConsoleStream;
	}
	
	
	
}
