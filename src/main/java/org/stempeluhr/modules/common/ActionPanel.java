package org.stempeluhr.modules.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ActionPanel extends ModulePanel {

	private static final long serialVersionUID = 1L;
	private static final int MaxTimer = 5000;
	private static final int TimerStep = 100;
	private static final String DefaultLableText = "Bitte Chip vor den Empfänger halten";

	private String titelMessage;
	private PanelController controller;

	private JTextField textField;
	private JLabel title;
	private JLabel actionLabel;
	private JProgressBar progressBar;
	private Timer timer;
	private int exceededTime;

	public ActionPanel() {
		this.setLayout(new BorderLayout());
		this.initProgressBar();
		this.initTextField();
		this.setBackground(Color.black);
		this.setVisible(false);
	}

	public void setTitel(String titel) {
		this.titelMessage = titel;
		this.initLabels();
	}

	public void setController(PanelController controller) {
		this.controller = controller;
		this.initController();
	}

	private void initController() {
		this.controller.setListener(this);
	}

	private void initLabels() {
		this.title = new JLabel();
		this.title.setText(this.titelMessage);
		this.title.setForeground(Color.WHITE);
		this.title.setBackground(Color.BLACK);
		this.title.setHorizontalAlignment(SwingConstants.CENTER);
		this.title.setFont(this.title.getFont().deriveFont(22f));
		this.title.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, Color.decode("#ff80fe")), new EmptyBorder(0, 0, 30, 0)));
		this.title.setOpaque(true);
		this.title.setPreferredSize(new Dimension(250, 70));
		this.add(this.title, BorderLayout.NORTH);

		this.actionLabel = new JLabel();
		this.actionLabel.setText(DefaultLableText);
		this.actionLabel.setForeground(Color.WHITE);
		this.actionLabel.setBackground(Color.BLACK);
		this.actionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.actionLabel.setFont(this.title.getFont().deriveFont(18f));
		this.actionLabel.setOpaque(true);
		this.actionLabel.setPreferredSize(new Dimension(250, 70));
		this.add(this.actionLabel);
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
		this.progressBar.setMaximum(MaxTimer);
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
		this.textField.setText("");
		this.textField.requestFocusInWindow();

		if (this.timer == null) {
			this.timer = new Timer(TimerStep, this);
			this.timer.start();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) e.getSource();
			if (e.getActionCommand().equals(Constants.CommandKeyPressed) && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
				this.timer.stop();
				this.setEnabled(false);
				String text = this.textField.getText();
				if (text != null && !text.isEmpty()) {
					this.controller.performAction(text);
					this.exceededTime = 0;
				}
			}
		} else if (e.getSource() == this.timer) {
			if (exceededTime == MaxTimer) {
				this.close();
			} else {
				this.exceededTime += TimerStep;
				this.progressBar.setValue(this.exceededTime);
			}
		} else if (e.getSource() instanceof PropertyChangeEvent) {
			PropertyChangeEvent evt = (PropertyChangeEvent) e.getSource();
			if (evt.getPropertyName().equals(Constants.CommandClose) && (Boolean) evt.getNewValue()) {
				this.close();
			} else if (evt.getPropertyName().equals(Constants.labelCahnged)) {
				this.actionLabel.setText((String) evt.getNewValue());
			}
		}
	}

	public void close() {
		if (this.timer.isRunning()) {
			this.timer.stop();
		}
		this.timer = null;
		this.actionLabel.setText(DefaultLableText);
		this.setVisible(false);
	}
}
