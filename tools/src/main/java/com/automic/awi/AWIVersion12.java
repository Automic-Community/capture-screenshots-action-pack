package com.automic.awi;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.automic.constants.Constants;
import com.automic.exception.AutomicException;
import com.automic.modal.AWI;
import com.automic.util.CommonUtil;
import com.automic.util.ConsoleWriter;

public class AWIVersion12 extends AbstractAWI{


	public AWIVersion12(AWI inputs) {
		super(inputs);
	}	

	public void loginAWI() throws AutomicException {
		
		try {
			
			// Connection
			WebElement con = driver.findElement(By.xpath(Constants.CONNECTION));
			con.sendKeys(inputs.getAeConnection());
			con.click();
			Thread.sleep(500);
			
			// Client
			WebElement client = driver.findElement(By.xpath(Constants.CLIENT));
			client.sendKeys(String.valueOf(inputs.getClient()));
			client.click();
			Thread.sleep(500);

			// Username
			WebElement username = driver.findElement(By.xpath(Constants.USERNAME));
			username.sendKeys(inputs.getUser());
			username.click();
			Thread.sleep(500);

			// Department
			if(CommonUtil.checkNotEmpty(inputs.getDepartment())) {
				WebElement department = driver.findElement(By.xpath(Constants.DEPARTMENT));
				department.sendKeys(inputs.getDepartment());
				department.click();
				Thread.sleep(500);
			}
			// password
			WebElement password = driver.findElement(By.xpath(Constants.PASSWORD));
			password.sendKeys(inputs.getPassword());
			password.click();
			Thread.sleep(100);

			// Login
			driver.findElement(By.xpath(Constants.LOGIN)).click();
			Thread.sleep(500);
			
			waitForElemetLoad(driver, "v-captiontext", inputs.getTimeOut());
			ConsoleWriter.writeln("Login sucessfully ...");
			Boolean isMyDashboardVisible = driver.findElement(By.className("v-captiontext")).isDisplayed();
			if(isMyDashboardVisible) {
				ConsoleWriter.writeln("DashBoard loaded successfully ...");
			}else {
				throw new AutomicException("ERROR: DashBoard loading failed ...");
			}
			
			ConsoleWriter.writeln("Waiting for 5 second to dashboard fully initialization ...");
			Thread.sleep(5000);
		} catch (InterruptedException | NoSuchElementException e) {			
			throw new AutomicException(e.getMessage());
		}
	}
	
	


	protected String formAWI12DashboardUrl(String dashboard) {

		return inputs.getAwiUrl() + "#" + inputs.getAeConnection() + "/0" + inputs.getClient() + "/@home/dashboards/"
				+ dashboard;
	}

	
}
