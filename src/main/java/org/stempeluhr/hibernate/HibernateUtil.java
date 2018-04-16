package org.stempeluhr.hibernate;

import java.net.ConnectException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.stempeluhr.modules.common.Constants;

public class HibernateUtil {
	private final SessionFactory dbSessionFactory;

	private static HibernateUtil instance = null;

	private HibernateUtil(DatabaseInfo dbInfo) {
		try {
			dbSessionFactory = HibernateInitalizer.initSessionFactory(dbInfo);
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void createInstance(DatabaseInfo dbInfo) throws ConnectException {
		instance = new HibernateUtil(dbInfo);
	}

	public static HibernateUtil getInstance() throws ConnectException {
		if (instance == null) {
			createInstance(getDatabaseInfo());
		}
		return instance;
	}

	public Session getDBSession() {
		return this.dbSessionFactory.openSession();
	}

	private static DatabaseInfo getDatabaseInfo() {
		DatabaseInfo databaseInfo = new DatabaseInfo();
		if (Constants.isDebug) {
			databaseInfo.setHost("127.0.0.1");
			databaseInfo.setPort(1433);
			databaseInfo.setInstanceName(null);
			databaseInfo.setDb("Zeiterfassung");
			databaseInfo.setUsername("sa");
			databaseInfo.setPassword("superGeheim1!");
		}

		return databaseInfo;
	}
}
