package com.automic.actions;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.automic.awi.AWIVersion12;
import com.automic.awi.AbstractAWI;
import com.automic.constants.Constants;
import com.automic.constants.ExceptionConstants;
import com.automic.exception.AutomicException;
import com.automic.modal.AWI;
import com.automic.util.CommonUtil;
import com.automic.util.ConsoleWriter;

public abstract class AbstractInitAction extends AbstractAction {

	private AbstractAWI v12;

	public AbstractInitAction() {

		addOption(Constants.AWI_VERSION, true, "AWI Version");
		addOption(Constants.IGNORE_SSL, true, "Ignore SSL");
		addOption(Constants.AWI_URL, true, "AWI URL");
		addOption(Constants.AWI_CONNECTION, true, "Connection");
		addOption(Constants.AWI_CLIENT, true, "AWI Client");
		addOption(Constants.AWI_USERNAME, true, "AWI Username");
		addOption(Constants.AWI_DEPARTMENT, false, "AWI Department");
		addOption(Constants.AWI_PASSWORD, true, "AWI Password");
		addOption(Constants.TIMEOUT, true, "Page load time out");
		addOption(Constants.DEBUG, false, "Debug");

	}

	@Override
	protected void execute() throws AutomicException {
		try {
			AWI awi = new AWI();
			initializeInput(awi);
			v12 = new AWIVersion12(awi);
			try {
				executeSpecific(v12);
			} catch (Exception e) {
				if (awi.isDebug()) {
					String tmpdir = System.getProperty("java.io.tmpdir");
					String fileName = String.join(String.valueOf(Thread.currentThread().getId()),
							"exception_screenshot.png");
					Path path = Paths.get(tmpdir, fileName);
					String filePath = path.toAbsolutePath().toString();
					v12.takeSnapshot(v12.getWebDriver(), filePath);
					ConsoleWriter.writeln("Exception occoured.");
					ConsoleWriter.writeln(String.format("Exception Screenshot is saved on path [%s]", filePath));
				}
				throw e;
			}
		} finally {
			if (v12 != null) {
				v12.getWebDriver().close();
			}
		}
	}

	private void initializeInput(AWI awi) throws AutomicException {
		awi.setAeVersion(CommonUtil.trim(getOptionValue(Constants.AWI_VERSION)));
		awi.setIgnoreSSL(CommonUtil.convert2Bool(getOptionValue(Constants.IGNORE_SSL)));
		awi.setAwiUrl(CommonUtil.trim(getOptionValue(Constants.AWI_URL)));
		awi.setAeConnection(CommonUtil.trim(getOptionValue(Constants.AWI_CONNECTION)));
		String client = CommonUtil.trim(getOptionValue(Constants.AWI_CLIENT));
		try {
			awi.setClient(Integer.parseInt(client));
		} catch (NumberFormatException e) {
			throw new AutomicException(String.format(ExceptionConstants.INVALID_CLIENT_TYPE, client));
		}
		awi.setUser(CommonUtil.trim(getOptionValue(Constants.AWI_USERNAME)));
		awi.setDepartment(CommonUtil.trim(getOptionValue(Constants.AWI_DEPARTMENT)));
		awi.setPassword(getOptionValue(Constants.AWI_PASSWORD));
		awi.setTimeOut(Integer.valueOf(CommonUtil.trim(getOptionValue(Constants.TIMEOUT))));
		awi.setDebug(CommonUtil.convert2Bool(getOptionValue(Constants.DEBUG)));
	}

	public abstract void executeSpecific(AbstractAWI v12) throws AutomicException;

}
