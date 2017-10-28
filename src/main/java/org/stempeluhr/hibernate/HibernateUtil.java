package org.stempeluhr.hibernate;

import java.net.ConnectException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
		try {
			instance = new HibernateUtil(dbInfo);
		} catch (ExceptionInInitializerError e) {
			e.printStackTrace();
			throw new ConnectException("Could not connect to database");
		}
	}

	public static HibernateUtil getInstance() throws ConnectException {
		if (instance == null) {
			createInstance(new DatabaseInfo());
		}
		return instance;
	}

	public Session getDBSession() {
		return this.dbSessionFactory.openSession();
	}

}
