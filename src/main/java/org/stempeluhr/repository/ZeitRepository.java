package org.stempeluhr.repository;

import java.net.ConnectException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.stempeluhr.hibernate.HibernateUtil;
import org.stempeluhr.model.detishdesign.Zeit;

public class ZeitRepository {

	public void save(Zeit Time) {
		try {
			Session session = HibernateUtil.getInstance().getDBSession();
			Transaction tx = session.beginTransaction();
			// tx.isActive()
			session.save(Time);
			tx.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Zeit Time) {
		try {
			Session session = HibernateUtil.getInstance().getDBSession();
			Transaction tx = session.beginTransaction();
			session.update(Time);
			tx.commit();
			session.close();

		} catch (ConnectException e) {
			e.printStackTrace();
		}
	}

	public Zeit getStartedTimeBy(long userID) {
		try {
			Session session = HibernateUtil.getInstance().getDBSession();
			Query<Zeit> query = session.createQuery("FROM Zeit WHERE userid = :userID AND endTime IS NULL ORDER BY startTime DESC", Zeit.class);
			query.setParameter("userID", userID);
			List<Zeit> user = query.list();
			session.close();
			if (user.isEmpty()) {
				return null;
			}

			return user.get(0);
		} catch (ConnectException e) {
			e.printStackTrace();
		}
		return null;
	}

}
