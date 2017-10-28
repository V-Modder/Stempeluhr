package org.stempeluhr.modules.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

public abstract class ModulePanel extends JPanel implements ActionListener, KeyListener, ControllerListener, MouseListener {

	private static final long serialVersionUID = 1L;

	public void keyPressed(KeyEvent e) {
		ActionEvent evt = new ActionEvent(e, ActionEvent.ACTION_PERFORMED, Constants.CommandKeyPressed);
		this.actionPerformed(evt);
	}

	public void keyReleased(KeyEvent e) {
		ActionEvent evt = new ActionEvent(e, ActionEvent.ACTION_PERFORMED, Constants.CommandKeyReleased);
		this.actionPerformed(evt);
	}

	public void keyTyped(KeyEvent e) {
		ActionEvent evt = new ActionEvent(e, ActionEvent.ACTION_PERFORMED, Constants.CommandKeyTyped);
		this.actionPerformed(evt);
	}

	public void propertyChange(PropertyChangeEvent event) {
		ActionEvent evt = new ActionEvent(event, ActionEvent.ACTION_PERFORMED, Constants.CommandKeyTyped);
		this.actionPerformed(evt);
	}

	public void mouseClicked(MouseEvent e) {
		ActionEvent evt = new ActionEvent(e, ActionEvent.ACTION_PERFORMED, Constants.CommandMouseClicked);
		this.actionPerformed(evt);
	}

	public void mousePressed(MouseEvent e) {
		ActionEvent evt = new ActionEvent(e, ActionEvent.ACTION_PERFORMED, Constants.CommandMousePressed);
		this.actionPerformed(evt);
	}

	public void mouseReleased(MouseEvent e) {
		ActionEvent evt = new ActionEvent(e, ActionEvent.ACTION_PERFORMED, Constants.CommandMouseReleased);
		this.actionPerformed(evt);
	}

	public void mouseEntered(MouseEvent e) {
		ActionEvent evt = new ActionEvent(e, ActionEvent.ACTION_PERFORMED, Constants.CommandMouseEntered);
		this.actionPerformed(evt);
	}

	public void mouseExited(MouseEvent e) {
		ActionEvent evt = new ActionEvent(e, ActionEvent.ACTION_PERFORMED, Constants.CommandMouseExited);
		this.actionPerformed(evt);
	}

	public abstract void actionPerformed(ActionEvent e);

}
