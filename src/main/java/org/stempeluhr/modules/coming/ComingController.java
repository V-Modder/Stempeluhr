package org.stempeluhr.modules.coming;

import java.beans.PropertyChangeEvent;
import java.util.Date;

import org.stempeluhr.model.detishdesign.Benutzer;
import org.stempeluhr.model.detishdesign.Zeit;
import org.stempeluhr.modules.common.BackgroundWorker;
import org.stempeluhr.modules.common.BackgroundWorker.Finished;
import org.stempeluhr.modules.common.BackgroundWorker.Parameters;
import org.stempeluhr.modules.common.BackgroundWorker.ProgressChanged;
import org.stempeluhr.modules.common.Constants;
import org.stempeluhr.modules.common.PanelController;
import org.stempeluhr.modules.common.Worker;
import org.stempeluhr.repository.BenutzerRepository;
import org.stempeluhr.repository.ZeitRepository;
import org.stempeluhr.util.Parser;

public class ComingController extends PanelController implements Worker {

	private static final String UserNotFound = "<html><body><div style='text-align: center;'><span style='font-size:20;color:red'>Achtung<br>Benutzer nicht gefunden</span></div></body></html>";
	private static final String UserAlreadyStartedToWork = "<html><body><div style='text-align: center;'><span style='font-size:20;color:red'>Achtung<br>Es wurde falsch gebucht.<br>Bitte erst GEHEN!</span></div></body></html>";
	private static final String HaveFun = "<html><body><div style='text-align: center;'><span style='font-size:20'>Viel Spaß beim Arbeiten<br><br><FIRTSNAME> <LASTNAME></span></div></body></html>";

	private BenutzerRepository benutzerRepository = new BenutzerRepository();
	private ZeitRepository zeitRepository = new ZeitRepository();

	public void setBenutzerRepository(BenutzerRepository benutzerRepository) {
		this.benutzerRepository = benutzerRepository;
	}

	public void setZeitRepository(ZeitRepository zeitRepository) {
		this.zeitRepository = zeitRepository;
	}

	@Override
	public void performAction(Object param) {
		BackgroundWorker bw = new BackgroundWorker(this);
		bw.runWorkerAsync(param.toString());
	}

	private String buildPersonalizedMessage(Benutzer user) {
		return HaveFun.replace("<FIRTSNAME>", user.getFirstname()).replace("<LASTNAME>", user.getLastname());
	}

	@Override
	public void doWork(Parameters args) {
		String param = (String) args.getParameter();
		long chipId = Parser.stringToLong(param);

		Benutzer user = this.benutzerRepository.getBy(chipId);
		if (user == null) {
			this.showMessage(UserNotFound);
			return;
		}

		if (this.zeitRepository.getStartedTimeBy(user.getId()) != null) {
			this.showMessage(UserAlreadyStartedToWork);
			return;
		}

		Zeit startTime = new Zeit();
		startTime.setUserid(user.getId());
		startTime.setStartTime(new Date());
		this.zeitRepository.save(startTime);

		this.showMessage(this.buildPersonalizedMessage(user));
	}

	@Override
	public void workerFinished(Finished args) {
		this.fireChangeEvent(new PropertyChangeEvent(this, Constants.CommandClose, null, true));
	}

	@Override
	public void progressChanged(ProgressChanged args) {
	}
}
