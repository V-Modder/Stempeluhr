package org.stempeluhr.repository;

import java.net.ConnectException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.stempeluhr.hibernate.HibernateUtil;
import org.stempeluhr.model.detishdesign.Benutzer;

public class BenutzerRepository {

	public Benutzer getBy(Long chipID) {
		try {
			Session session = HibernateUtil.getInstance().getDBSession();

			Query<Benutzer> query = session.createQuery("FROM Benutzer WHERE chipID = :chipID", Benutzer.class);
			query.setParameter("chipID", chipID);
			List<Benutzer> userList = query.list();

			session.close();
			if (userList.isEmpty()) {
				return null;
			}

			return userList.get(0);
		} catch (ConnectException e) {
			e.printStackTrace();
			return null;
		}
	}
}
