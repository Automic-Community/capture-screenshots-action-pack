package analytics;

import java.util.Arrays;

public class Environment {

	private String aeVersion;
	private String aeConnection;
	private String awiUrl;
	private String client;
	private String user;
	private String department;
	private String password;

	private String dashboard;
	private String [] widgets;

	private String dashboadURL;
	private String folderPath;

	public String getAeVersion() {
		return aeVersion;
	}

	public String getAeConnection() {
		return aeConnection;
	}

	public void setAeVersion(String aeVersion) {
		this.aeVersion = aeVersion;
	}

	public void setAeConnection(String aeConnection) {
		this.aeConnection = aeConnection;
	}

	public String getAwiUrl() {
		return awiUrl;
	}

	public void setAwiUrl(String awiUrl) {
		this.awiUrl = awiUrl;
	}

	public String getClient() {
		return client;
	}

	public String getUser() {
		return user;
	}

	public String getDepartment() {
		return department;
	}

	public String getPassword() {
		return password;
	}

	public String getDashboard() {
		return dashboard;
	}

	
	public void setClient(String client) {
		this.client = client;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}

	

	/**
	 * @return the dashboadURL
	 */
	public String getDashboadURL() {
		return dashboadURL;
	}

	/**
	 * @return the widgets
	 */
	public String[] getWidgets() {
		return widgets;
	}

	/**
	 * @param widgets the widgets to set
	 */
	public void setWidgets(String[] widgets) {
		this.widgets = widgets;
	}

	/**
	 * @param dashboadURL the dashboadURL to set
	 */
	public void setDashboadURL(String dashboadURL) {
		this.dashboadURL = dashboadURL;
	}

	/**
	 * @return the folderPath
	 */
	public String getFolderPath() {
		return folderPath;
	}

	/**
	 * @param folderPath the folderPath to set
	 */
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	@Override
	public String toString() {
		return "Environment [aeVersion=" + aeVersion + ", aeConnection=" + aeConnection + ", awiUrl=" + awiUrl
				+ ", client=" + client + ", user=" + user + ", department=" + department + ", password=" + password
				+ ", dashboard=" + dashboard + ", widgets=" + Arrays.toString(widgets) + ", dashboadURL=" + dashboadURL
				+ ", folderPath=" + folderPath + "]";
	}

	

}
