package org.stempeluhr.modules.common;

import java.beans.PropertyChangeEvent;

public abstract class PanelController extends Controller {

	public abstract void performAction(Object param);

	public void showMessage(String msg) {
		this.fireChangeEvent(new PropertyChangeEvent(this, Constants.labelCahnged, null, msg));
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
