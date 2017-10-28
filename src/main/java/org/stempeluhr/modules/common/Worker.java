package org.stempeluhr.modules.common;

public interface Worker {

	public void doWork(BackgroundWorker.Parameters args);

	public void workerFinished(BackgroundWorker.Finished args);

	public void progressChanged(BackgroundWorker.ProgressChanged args);
}
