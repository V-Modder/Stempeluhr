package org.stempeluhr.hibernate;

public class DatabaseInfo {
	private static final String DEFAULT_HOST = "hauptserver";
	private static final int DEFAULT_PORT = 1433;
	private static final String DEFAULT_USERNAME = "sa";
	private static final String DEFAULT_PASSWORD = "Basti1990";
	private static final String DEFAULT_INSTANCE_NAME = "FETISHDESIGN";
	private static final String DEFAULT_DB = "Zeiterfassung";

	private String host;
	private int port;
	private String username;
	private String password;
	private String instanceName;
	private String db;

	public DatabaseInfo() {
		setHost(DEFAULT_HOST);
		setPort(DEFAULT_PORT);
		setUsername(DEFAULT_USERNAME);
		setPassword(DEFAULT_PASSWORD);
		setInstanceName(DEFAULT_INSTANCE_NAME);
		setDb(DEFAULT_DB);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
}
