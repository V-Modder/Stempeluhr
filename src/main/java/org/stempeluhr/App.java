package org.stempeluhr;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.stempeluhr.modules.common.Constants;
import org.stempeluhr.modules.main_frame.MainFrame;

public class App {

	public final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		if (Arrays.stream(args).anyMatch(arg -> arg.equals("-debug"))) {
			Constants.isDebug = true;
			Logger.getRootLogger().setLevel(Level.DEBUG);
			PatternLayout layout = new PatternLayout();
			layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
			RollingFileAppender fileAppender = null;
			try {
				fileAppender = new RollingFileAppender(layout, "debug.log", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.addAppender(fileAppender);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
