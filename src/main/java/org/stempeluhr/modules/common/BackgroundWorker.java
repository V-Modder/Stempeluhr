package org.stempeluhr.modules.common;

import javax.swing.SwingUtilities;

public class BackgroundWorker {
	private Thread thread;
	private boolean workerStarted;
	private Worker worker;

	private volatile Parameters parameters;
	private volatile Object parameter;
	private volatile Exception runException;

	public BackgroundWorker(Worker worker) {
		this.worker = worker;
		this.thread = new Thread() {
			public void run() {
				parameters = new Parameters(parameter, false);

				try {
					BackgroundWorker.this.worker.doWork(parameters);
				} catch (Exception ex) {
					runException = ex;
				}

				try {
					final Finished finished = new Finished(runException);

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							BackgroundWorker.this.worker.workerFinished(finished);
						}
					});
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	// protected abstract void onDoWork(BackgroundWorker.Parameters args);
	//
	// protected abstract void onWorkerDone(BackgroundWorker.Finished args);
	//
	// protected abstract void onProgressChanged(BackgroundWorker.ProgressChanged
	// args);

	public void cancelWorkerAsync() {
		this.parameters.setCancelled();
	}

	public synchronized void runWorkerAsync() {
		runWorkerAsync(null);
	}

	public synchronized void runWorkerAsync(Object parameter) {
		this.parameter = parameter;

		if (!workerStarted && !this.thread.isAlive()) {
			workerStarted = true;
			this.thread.start();
		}
	}

	public void reportProgress(int progress) {
		reportProgress(progress, null);
	}

	public void reportProgress(final int progress, final Object userState) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ProgressChanged arguments = new ProgressChanged(progress, userState);
				BackgroundWorker.this.worker.progressChanged(arguments);
			}
		});
	}

	public class Parameters {
		private final Object parameter;
		private volatile boolean cancelled;

		public Parameters(Object parameter, boolean cancelled) {
			this.parameter = parameter;
			this.cancelled = cancelled;
		}

		public Object getParameter() {
			return this.parameter;
		}

		public boolean isCancelled() {
			return this.cancelled;
		}

		private void setCancelled() {
			this.cancelled = true;
		}
	}

	public class ProgressChanged {
		private final int progress;
		private final Object userState;

		public ProgressChanged(int progress, Object userState) {
			this.progress = progress;
			this.userState = userState;
		}

		public int getProgress() {
			return this.progress;
		}

		public Object getUserState() {
			return this.userState;
		}
	}

	public class Finished {
		private final Exception exception;

		public Finished(Exception exception) {
			this.exception = exception;
		}

		public Exception getException() {
			return this.exception;
		}
	}
}
