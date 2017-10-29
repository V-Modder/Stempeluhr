package org.stempeluhr.modules.going;

import java.beans.PropertyChangeEvent;
import java.util.Date;

import org.stempeluhr.model.detishdesign.Benutzer;
import org.stempeluhr.model.detishdesign.Zeit;
import org.stempeluhr.modules.common.BackgroundWorker;
import org.stempeluhr.modules.common.BackgroundWorker.Finished;
import org.stempeluhr.modules.common.BackgroundWorker.Parameters;
import org.stempeluhr.modules.common.BackgroundWorker.ProgressChanged;
import org.stempeluhr.modules.common.Constants;
import org.stempeluhr.modules.common.Controller;
import org.stempeluhr.modules.common.Worker;
import org.stempeluhr.repository.BenutzerRepository;
import org.stempeluhr.repository.ZeitRepository;
import org.stempeluhr.util.Parser;

public class GoingController extends Controller implements Worker {

	private static final String userNotFound = "<html><body><div style='text-align: center;'><span style='font-size:20;color:red'>Achtung<br>Benutzer nicht gefunden</span></div></body></html>";
	private static final String wrongAction = "<html><body><div style='text-align: center;'><span style='font-size:20;color:red'>Achtung<br>Es wurde falsch gebucht.<br>Bitte erst GEHEN!</span></div></body></html>";
	private static final String haveFun = "<html><body><div style='text-align: center;'><span style='font-size:20'>Viel Spass beim Arbeiten<br><br><FIRTSNAME> <LASTNAME></span></div></body></html>";

	private BenutzerRepository benutzerRepository;
	private ZeitRepository zeitRepository;

	public void going(String chipIDStr) {
		BackgroundWorker bw = new BackgroundWorker();
		bw.runWorkerAsync(chipIDStr);
	}

	public void doWork(Parameters args) {
		String param = (String) args.getParameter();
		long chipID = Parser.stringToLong(param);
		Benutzer user = this.benutzerRepository.getBy(chipID);
		if (user == null) {
			this.showMessage(userNotFound);
			return;
		}
		Zeit iscome = this.zeitRepository.getStartedTimeBy(user.getId());
		if (iscome != null) {
			this.showMessage(wrongAction);
			return;
		}
		Zeit startTime = new Zeit();
		startTime.setUserid(user.getId());
		startTime.setStartTime(new Date());
		this.zeitRepository.save(startTime);

		String text = haveFun;
		text = text.replace("<FIRSTNAME>", user.getFirstname());
		text = text.replace("<LASTNAME>", user.getLastname());
		this.showMessage(text);
	}

	public void showMessage(String msg) {
		this.fireChangeEvent(new PropertyChangeEvent(this, Constants.labelCahnged, null, msg));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void workerFinished(Finished args) {
		this.fireChangeEvent(new PropertyChangeEvent(this, Constants.CommandClose, null, true));
	}

	public void progressChanged(ProgressChanged args) {
	}
}
