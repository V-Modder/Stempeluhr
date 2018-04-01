package org.stempeluhr.repository;

import java.net.ConnectException;

import org.hibernate.Session;
import org.stempeluhr.hibernate.HibernateUtil;

public class ConnectionTestRepository {

	public boolean testDbConnection() {
		try {
			Session session = HibernateUtil.getInstance().getDBSession();

			if (!session.isConnected()) {
				return false;
			} else {
				session.close();
				return true;
			}
		} catch (ConnectException e) {
			e.printStackTrace();
			return false;
		}
	}
}
