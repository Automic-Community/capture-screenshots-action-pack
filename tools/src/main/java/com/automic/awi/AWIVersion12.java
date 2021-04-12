package com.automic.awi;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automic.constants.Constants;
import com.automic.exception.AutomicException;
import com.automic.modal.AWI;
import com.automic.util.CommonUtil;
import com.automic.util.ConsoleWriter;

public class AWIVersion12 extends AbstractAWI {

	public AWIVersion12(AWI inputs) {
		super(inputs);
	}

	public void loginAWI() throws AutomicException {

		try {

			WebElement loginDataDiv = driver.findElement(By.cssSelector(Constants.LOGIN_DATA_DIV));
			List<WebElement> inputElements = loginDataDiv.findElements(By.cssSelector(Constants.LOGIN_DATA_DIV_INPUTS));

			WebElement loginButtonDiv = driver.findElement(By.cssSelector(Constants.LOGIN));

			if (inputElements != null && inputElements.size() == 5) {

				// Connection
				setFieldValue(inputElements.get(0), inputs.getAeConnection(), 1000, false);
				ConsoleWriter.writeln(String.format("Connection field value is set[%s]", inputs.getAeConnection()));

				// Client
				setFieldValue(inputElements.get(1), String.valueOf(inputs.getClient()), 1000, true);
				ConsoleWriter.writeln(String.format("Client field value is set[%s]", inputs.getClient()));

				// Username
				setFieldValue(inputElements.get(2), inputs.getUser(), 1000, true);
				ConsoleWriter.writeln(String.format("Username field value is set[%s]", inputs.getUser()));

				// Department
				if (CommonUtil.checkNotEmpty(inputs.getDepartment())) {
					setFieldValue(inputElements.get(3), inputs.getDepartment(), 1000, true);
					ConsoleWriter.writeln(String.format("Department field value is set[%s]", inputs.getDepartment()));
				}

				// password
				setFieldValue(inputElements.get(4), inputs.getPassword(), 1000, true);
				ConsoleWriter.writeln(String.format("Password field value is set[%s]", "*********"));

				Thread.sleep(1000);
				// submit login page
				new WebDriverWait(getWebDriver(), 3).until(ExpectedConditions.elementToBeClickable(loginButtonDiv))
						.click();
				ConsoleWriter.writeln(String.format("Page is submitted..."));

				Thread.sleep(5000);

			} else {
				if (inputElements != null) {
					throw new AutomicException(String.format(
							"ERROR: Unable to get input elements in required format. Retrived input elements[%s]",
							inputElements.size()));
				} else {
					throw new AutomicException("Unable to initialize Input Element object.");
				}
			}

			waitForElementLoad(driver, "div.uc4_framework_header_homeButton", inputs.getTimeOut());

			ConsoleWriter.writeln("Login sucessfully ...");

			ConsoleWriter.writeln("Waiting for 5 second to dashboard widgets initialization ...");
			Thread.sleep(5000);
		} catch (InterruptedException | NoSuchElementException e) {
			ConsoleWriter.writeln(e);
			throw new AutomicException(e.getMessage());
		}
	}

	protected String formAWIDashboardUrl(String dashboard) {

		return inputs.getAwiUrl() + "#" + inputs.getAeConnection() + "/0" + inputs.getClient() + "/@home/dashboards/"
				+ dashboard;
	}

}
