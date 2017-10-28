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
			List<Benutzer> user = query.list();

			session.close();
			if (user.isEmpty()) {
				return null;
			}

			return user.get(0);
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		return null;	
		}
	//	return null;
	}
	public boolean testConnection() {
		try {
			Session session = HibernateUtil.getInstance().getDBSession();

			if (!session.isConnected())
			{
				return false;
			}
			else
			{
				session.close();
				return true;
			}
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		return false;	
		}
	}
}
