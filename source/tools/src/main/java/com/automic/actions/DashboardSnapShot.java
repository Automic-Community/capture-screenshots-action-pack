package com.automic.actions;

import java.io.File;

import com.automic.awi.AbstractAWI;
import com.automic.constants.Constants;
import com.automic.constants.ExceptionConstants;
import com.automic.exception.AutomicException;
import com.automic.util.CommonUtil;
import com.automic.util.ConsoleWriter;

public class DashboardSnapShot extends AbstractInitAction {
	private String dashboardName;
	private String filePath;
	private Integer widgetTimeout;

	public DashboardSnapShot() {
		this.addOption(Constants.DASHBOARD, true, "Dashboard Name");
		this.addOption(Constants.FILE_PATH, true, "Absolute file path");
		this.addOption(Constants.WIDGET_TIMEOUT, true, "Widget load time out");
	}

	@Override
	public void executeSpecific(AbstractAWI v12) throws AutomicException {
		initializeInput();
		validation();
		v12.loadDashboard(dashboardName);
		v12.loginAWI();
		ConsoleWriter.writeln(String.format(Constants.WIDGET_WAIT_TIME_MSG, widgetTimeout));
		v12.waitTime(widgetTimeout);
		v12.takeDashboardSnapshot(filePath);
		ConsoleWriter.writeln("Dashboard Captured successfully..");
	}

	private void initializeInput() throws AutomicException {

		dashboardName = CommonUtil.trim(getOptionValue(Constants.DASHBOARD));
		filePath = CommonUtil.trim(getOptionValue(Constants.FILE_PATH));
		widgetTimeout =Integer.valueOf(CommonUtil.trim(getOptionValue(Constants.WIDGET_TIMEOUT)));
	}


	private void validation() throws AutomicException{
		File f = new File(filePath);
		if(!CommonUtil.checkPNG(f)) {
			throw new AutomicException(String.format(ExceptionConstants.INVALID_FILE_TYPE, f.getName()));
		}
	}
}
