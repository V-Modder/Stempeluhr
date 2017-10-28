package org.stempeluhr.hibernate;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.stempeluhr.model.detishdesign.Benutzer;
import org.stempeluhr.model.detishdesign.Zeit;

public class HibernateInitalizer {

	public static final String CONNECTION_PARAMETER = "?zeroDateTimeBehavior=convertToNull&useTimezone=false";

	public static SessionFactory initSessionFactory(DatabaseInfo dbInfo) {
		Properties props = new Properties();

		props.setProperty("hibernate.connection.url", "jdbc:sqlserver://" + dbInfo.getHost() + "\\" + dbInfo.getInstanceName() + ";databaseName=" + dbInfo.getDb());
		props.setProperty("hibernate.connection.username", dbInfo.getUsername());
		props.setProperty("hibernate.connection.password", dbInfo.getPassword());
		props.setProperty("dialect", "org.hibernate.dialect.SQLServerDialect");

		Configuration config = new Configuration();
		config.addPackage("com.concretepage.persistence");
		config.addProperties(props);

		config.addAnnotatedClass(Benutzer.class);
		config.addAnnotatedClass(Zeit.class);

		return config.buildSessionFactory();
	}
}
