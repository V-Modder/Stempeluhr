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

		props.setProperty("hibernate.connection.url", generateConnectionUrl(dbInfo));
		props.setProperty("hibernate.connection.username", dbInfo.getUsername());
		props.setProperty("hibernate.connection.password", dbInfo.getPassword());
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.cache.use_query_cache", "true");
		props.setProperty("hibernate.cache.use_second_level_cache", "true");
		props.setProperty("hibernate.cache.default_cache_concurrency_strategy", "READ_WRITE");
		props.setProperty("dialect", "org.hibernate.dialect.SQLServerDialect");

		props.setProperty("hibernate.connection.provider_class", "com.zaxxer.hikari.hibernate.HikariConnectionProvider");
		props.setProperty("hibernate.hikari.connectionTimeout", "5000");
		props.setProperty("hibernate.hikari.minimumIdle", "1");
		props.setProperty("hibernate.hikari.maximumPoolSize", "5");
		props.setProperty("hibernate.hikari.idleTimeout", "100");

		Configuration config = new Configuration();
		config.addPackage("com.concretepage.persistence");
		config.addProperties(props);

		config.addAnnotatedClass(Benutzer.class);
		config.addAnnotatedClass(Zeit.class);

		return config.buildSessionFactory();
	}

	private static String generateConnectionUrl(DatabaseInfo dbInfo) {
		String connection = "jdbc:sqlserver://";

		connection += dbInfo.getHost();
		if (dbInfo.getInstanceName() != null) {
			connection += "\\" + dbInfo.getInstanceName();
		}
		if (dbInfo.getPort() != null && dbInfo.getPort() != 1433) {
			connection += ":" + dbInfo.getPort();
		}
		if (dbInfo.getDb() != null) {
			connection += ";databaseName=" + dbInfo.getDb();
		}

		return connection;
	}
}
