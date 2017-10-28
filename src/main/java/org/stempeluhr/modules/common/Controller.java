package org.stempeluhr.modules.common;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public class Controller {
	List<ControllerListener> listeners;

	public Controller() {
		this.listeners = new ArrayList<ControllerListener>();
	}

	public void setListener(ControllerListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(ControllerListener listener) {
		this.listeners.remove(listener);
	}

	protected void fireChangeEvent(final PropertyChangeEvent event) {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Controller.this.fireChangeEvent(event);
				}
			});
		}

		for (ControllerListener listener : this.listeners) {
			listener.propertyChange(event);
		}
	}
}
