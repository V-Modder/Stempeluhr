package org.stempeluhr;

import java.awt.EventQueue;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.stempeluhr.modules.common.Constants;
import org.stempeluhr.modules.main_frame.MainFrame;

public class App {

	public final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		if (Arrays.stream(args).anyMatch(arg -> arg.equals("-debug"))) {
			Constants.isDebug = true;
			Logger.getRootLogger().setLevel(Level.DEBUG);
		}
		if (Arrays.stream(args).anyMatch(arg -> arg.equals("-v"))) {
			Logger.getRootLogger().setLevel(Level.DEBUG);
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
