package org.stempeluhr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;
import javax.swing.Timer;

public class ProgressbarTimer implements ActionListener{
	
	private static ProgressbarTimer progressbarTimer;
	
	public static ProgressbarTimer getInstance(JProgressBar progressbar) {
		if(progressbarTimer == null) {
			progressbarTimer = new ProgressbarTimer(progressbar);
		}
		
		return progressbarTimer;
	}
	
	private JProgressBar progressbar;
	private Timer timer;

	public ProgressbarTimer(JProgressBar progressbar) {
		this.progressbar = progressbar;
	}

	public void startTimer() {
		if (this.timer == null) {
			this.timer = new Timer(500, this);
			this.timer.start();
			this.progressbar.setVisible(true);
		}
		this.progressbar.setValue(500);	
	}
	
	public void actionPerformed(ActionEvent e) {
		if(this.progressbar.getValue() < this.progressbar.getMaximum()) {
			this.progressbar.setValue(this.progressbar.getValue() + 500);
		}
		else {
			this.progressbar.setValue(500);
			this.progressbar.setVisible(false);
			this.timer.stop();
			this.timer = null;
		}
	}
}
