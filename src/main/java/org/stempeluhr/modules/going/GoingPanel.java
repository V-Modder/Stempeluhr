package org.stempeluhr.modules.going;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.stempeluhr.modules.common.Constants;
import org.stempeluhr.modules.common.ModulePanel;

public class GoingPanel extends ModulePanel {

	private static final long serialVersionUID = 1L;
	private static final int maxTimer = 5000;
	private static final int timerStep = 100;
	private GoingController goingController;
	private JTextField textField;
	private JLabel label;
	private JProgressBar progressBar;
	private Timer timer;
	private int exceededTime;

	public GoingPanel() {
		this.initController();
		this.setLayout(new BorderLayout());
		this.initLabel();
		this.initProgressBar();
		this.initTextField();
		this.setBackground(Color.black);
		this.setVisible(false);
	}

	private void initController() {
		this.goingController = new GoingController();
		this.goingController.setListener(this);
	}

	private void initLabel() {
		this.label = new JLabel();
		this.label.setText("Going");
		this.label.setForeground(Color.WHITE);
		this.label.setBackground(Color.BLACK);
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		this.label.setFont(this.label.getFont().deriveFont(22f));
		this.label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, Color.decode("#ff80fe")), new EmptyBorder(0, 0, 30, 0)));
		this.label.setOpaque(true);
		this.label.setPreferredSize(new Dimension(250, 70));
		this.add(this.label, BorderLayout.NORTH);
	}

	private void initTextField() {
		this.textField = new JTextField();
		this.textField.addKeyListener(this);
		this.textField.setBackground(Color.black);
		this.textField.setForeground(Color.black);
		this.textField.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 2));
		this.textField.setCaretColor(Color.black);
		this.textField.setColumns(80);
		this.add(textField, BorderLayout.CENTER);
	}

	private void initProgressBar() {
		this.progressBar = new JProgressBar();
		this.progressBar.setMaximum(maxTimer);
		this.progressBar.setValue(0);
		this.progressBar.setBorderPainted(false);
		this.progressBar.setBorder(BorderFactory.createLineBorder(Color.black));
		this.progressBar.setForeground(Color.decode("#ff80fe"));
		this.progressBar.setBackground(Color.black);
		this.progressBar.setPreferredSize(new Dimension(250, 3));
		this.add(this.progressBar, BorderLayout.SOUTH);
	}

	public void showPanel() {
		this.exceededTime = 0;
		this.progressBar.setValue(0);
		this.setVisible(true);
		this.textField.requestFocusInWindow();

		if (this.timer == null) {
			this.timer = new Timer(timerStep, this);
			this.timer.start();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) e.getSource();
			if (e.getActionCommand().equals(Constants.CommandKeyPressed) && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
				this.timer.stop();
				String text = this.textField.getText();
				if (text != null && !text.isEmpty()) {
					this.goingController.going(text);
					this.exceededTime = 0;
					this.textField.setEnabled(false);
				}
				this.close();
			}
		} else if (e.getSource() == this.timer) {
			if (exceededTime == maxTimer) {
				this.close();
			} else {
				this.exceededTime += timerStep;
				this.progressBar.setValue(this.exceededTime);
			}
		} else if (e.getActionCommand().equals(Constants.CommandClose)) {
			this.close();
		}
	}

	public void close() {
		if (this.timer.isRunning()) {
			this.timer.stop();
		}
		this.timer = null;
		this.setVisible(false);
	}
}
