package com.automic.modal;

public class AWI {
	private String aeVersion;
	private String awiUrl;
	private String aeConnection;
	private Integer client;
	private String user;
	private String department;
	private String password;
	private Integer timeOut;
	private boolean ignoreSSL;
	private boolean debug;

	/**
	 * @return the aeVersion
	 */
	public String getAeVersion() {
		return aeVersion;
	}

	/**
	 * @return the awiUrl
	 */
	public String getAwiUrl() {
		return awiUrl;
	}

	/**
	 * @return the aeConnection
	 */
	public String getAeConnection() {
		return aeConnection;
	}

	/**
	 * @return the client
	 */
	public Integer getClient() {
		return client;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param aeVersion the aeVersion to set
	 */
	public void setAeVersion(String aeVersion) {
		this.aeVersion = aeVersion;
	}

	/**
	 * @param awiUrl the awiUrl to set
	 */
	public void setAwiUrl(String awiUrl) {
		this.awiUrl = awiUrl;
	}

	/**
	 * @param aeConnection the aeConnection to set
	 */
	public void setAeConnection(String aeConnection) {
		this.aeConnection = aeConnection;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Integer client) {
		this.client = client;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the timeOut
	 */
	public Integer getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut the timeOut to set
	 */
	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * @return the ignoreSSL
	 */
	public boolean isIgnoreSSL() {
		return ignoreSSL;
	}

	/**
	 * @param ignoreSSL the ignoreSSL to set
	 */
	public void setIgnoreSSL(boolean ignoreSSL) {
		this.ignoreSSL = ignoreSSL;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
