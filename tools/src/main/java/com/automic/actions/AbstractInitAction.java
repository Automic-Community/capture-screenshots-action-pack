package com.automic.actions;

import com.automic.awi.AWIVersion12;
import com.automic.awi.AbstractAWI;
import com.automic.constants.Constants;
import com.automic.exception.AutomicException;
import com.automic.modal.AWI;
import com.automic.util.CommonUtil;

public abstract class AbstractInitAction extends AbstractAction {

	
	private AbstractAWI v12;

	public AbstractInitAction() {

		addOption(Constants.AWI_VERSION, true, "AWI Version");
		addOption(Constants.IGNORE_SSL, true, "Ignore SSL");
		addOption(Constants.AWI_URL, true, "AWI URL");
		addOption(Constants.AWI_CONNECTION, true, "Connection");
		addOption(Constants.AWI_CLIENT, true, "AWI Client");
		addOption(Constants.AWI_USERNAME, true, "AWI Username");
		addOption(Constants.AWI_DEPARTMENT, true, "AWI Department");
		addOption(Constants.AWI_PASSWORD, true, "AWI Password");
		addOption(Constants.TIMEOUT, true, "Page load time out");

	}

	@Override
	protected void execute() throws AutomicException {
		try {
			AWI awi = new AWI();
			initializeInput(awi);
			v12 = new AWIVersion12(awi);
			executeSpecific(v12);
		} finally {
			if (v12 != null) {
				v12.getDriver().close();
			}
		}
	}

	private void initializeInput(AWI awi) throws AutomicException {
		awi.setAeVersion(CommonUtil.trim(getOptionValue(Constants.AWI_VERSION)));
		awi.setIgnoreSSL(CommonUtil.convert2Bool(getOptionValue(Constants.IGNORE_SSL)));
		awi.setAwiUrl(CommonUtil.trim(getOptionValue(Constants.AWI_URL)));
		awi.setAeConnection(CommonUtil.trim(getOptionValue(Constants.AWI_CONNECTION)));
		String client = CommonUtil.trim(getOptionValue(Constants.AWI_CLIENT));
		awi.setClient(Integer.parseInt(client));
		awi.setUser(CommonUtil.trim(getOptionValue(Constants.AWI_USERNAME)));
		awi.setDepartment(CommonUtil.trim(getOptionValue(Constants.AWI_DEPARTMENT)));
		awi.setPassword(getOptionValue(Constants.AWI_PASSWORD));
		awi.setTimeOut(Integer.valueOf(CommonUtil.trim(getOptionValue(Constants.TIMEOUT))));
	}

	public abstract void executeSpecific(AbstractAWI v12) throws AutomicException;

}
