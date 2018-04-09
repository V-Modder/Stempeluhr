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
import org.stempeluhr.modules.common.PanelController;
import org.stempeluhr.modules.common.Worker;
import org.stempeluhr.repository.BenutzerRepository;
import org.stempeluhr.repository.ZeitRepository;
import org.stempeluhr.util.DateHelper;
import org.stempeluhr.util.Parser;

public class GoingController extends PanelController implements Worker {

	private static final String UserNotFound = "<html><body><div style='text-align: center;'><span style='font-size:20;color:red'>Achtung<br>Benutzer nicht gefunden</span></div></body></html>";
	private static final String UserHasNotComeYet = "<html><body><div style='text-align: center;'><span style='font-size:20;color:red'>Achtung<br>Es wurde falsch gebucht.<br>Bitte erst kommen!</span></div></body></html>";
	private static final String ThanksForUsing = "<html><body><div style='text-align: center;'><span style='font-size:20'>Vielen Dank<br><FIRTSNAME> <LASTNAME><br>Schönen Feierabend<br>Gerabeitete Zeit: <TIME></span></div></body></html>";

	private BenutzerRepository benutzerRepository = new BenutzerRepository();
	private ZeitRepository zeitRepository = new ZeitRepository();

	public void setBenutzerRepository(BenutzerRepository benutzerRepository) {
		this.benutzerRepository = benutzerRepository;
	}

	public void setZeitRepository(ZeitRepository zeitRepository) {
		this.zeitRepository = zeitRepository;
	}

	public void doWork(Parameters args) {
		String param = (String) args.getParameter();
		long chipID = Parser.stringToLong(param);
		Benutzer user = this.benutzerRepository.getBy(chipID);
		if (user == null) {
			this.showMessage(UserNotFound);
			return;
		}
		Zeit comingTime = this.zeitRepository.getStartedTimeBy(user.getId());
		if (comingTime == null) {
			this.showMessage(UserHasNotComeYet);
			return;
		}

		comingTime.setEndTime(new Date());
		double workTime = this.calcTotalTime(comingTime.getStartTime(), comingTime.getEndTime());
		comingTime.setTotalTime(workTime);
		this.zeitRepository.update(comingTime);

		this.showMessage(this.buildPersonalizedMessage(user, workTime));
	}


	public void workerFinished(Finished args) {
		this.fireChangeEvent(new PropertyChangeEvent(this, Constants.CommandClose, null, true));
	}

	public void progressChanged(ProgressChanged args) {
	}

	@Override
	public void performAction(Object param) {
		BackgroundWorker bw = new BackgroundWorker(this);
		bw.runWorkerAsync(param.toString());
	}

	private String buildPersonalizedMessage(Benutzer user, double time) {
		int hour = (int) time;
		double minute = time - (int) (time);
		minute = Math.round(minute * 60);
		String workingTime = "";
		if (hour > 0) {
			workingTime += (int) hour + "Std ";
		}
		workingTime += (int) minute + "Min";
		
		return ThanksForUsing.replace("<FIRTSNAME>", user.getFirstname())
				.replace("<LASTNAME>", user.getLastname())
				.replace("<TIME>", workingTime);
	}
	
	private Double calcTotalTime(Date start, Date end) {
		return DateHelper.getDifferenceInHours(start, end);
	}
}
