package com.automic.awi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automic.constants.Constants;
import com.automic.constants.ExceptionConstants;
import com.automic.exception.AutomicException;
import com.automic.modal.AWI;
import com.automic.util.CommonUtil;
import com.automic.util.ConsoleWriter;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public abstract class AbstractAWI {

	protected final AWI inputs;
	protected WebDriver driver;

	public AbstractAWI(AWI inputs) {
		this.inputs = inputs;
		initWebDriver(inputs.isIgnoreSSL(), inputs.isDebug());		
		ConsoleWriter.writeln(inputs);

	}

	public void loadDashboard(String dashboard) throws AutomicException {
		String dashboardURL = formAWIDashboardUrl(dashboard);
		String dashboardURLMSG = "Dashboard URL[%s]";
		ConsoleWriter.writeln(String.format(dashboardURLMSG, dashboardURL));
		try {
			driver.get(dashboardURL);
		} catch (WebDriverException e) {
			String msg= e.getMessage();
			if(CommonUtil.checkNotEmpty(msg) && msg.contains(ExceptionConstants.ERR_CONNECTION_TIMED_OUT)) {
				throw new AutomicException(String.format(ExceptionConstants.URL_NOT_REACHNABLE, inputs.getAwiUrl()));
			}else {
				throw new AutomicException(e.getMessage());
			}
			
		}
		// wait for page until login button is clickable
		waitForelementToBeClickable(driver, Constants.LOGIN, inputs.getTimeOut());
		ConsoleWriter.writeln("Login page loaded ...");
	}

	public void takeDashboardSnapshot(String filePath) throws AutomicException {
		WebElement dashboard = driver.findElement(By.className("uc4-dashboard-layout"));
		takeSnapshot(driver, dashboard, filePath);
	}

	protected void takeWidgetSnapshot(String widgetName, String folderPath) throws AutomicException {

		List<WebElement> widgets = driver.findElements(By.className("v-gridlayout-slot"));

		for (WebElement webElement : widgets) {

			WebElement t = webElement.findElement(By.className("uc4-dashboard-widget-header-text"));
			String name = t.getText();
			if (widgetName.equals(name)) {
				takeSnapshot(driver, webElement, folderPath + name + ".png");
			}
		}
	}

	/**
	 * @return the driver
	 */
	public WebDriver getWebDriver() {
		return driver;
	}

	public void waitForelementToBeClickable(WebDriver driver, String lastElementToLoad, int waitTime) throws AutomicException {
		ExpectedCondition<Boolean> pageLoadCondition = waitDocumentReadyObj();
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		try {
			wait.until(pageLoadCondition);
		} catch (TimeoutException e) {
			throw new AutomicException(String.format(ExceptionConstants.LOGIN_PAGE_TIMEOUT, waitTime));
		}
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(lastElementToLoad)));
		} catch (TimeoutException e) {
			throw new AutomicException(String.format(ExceptionConstants.PAGE_INPUTS_TIMEOUT, waitTime));
		}
	}

	/**
	 * Check if Element is visible
	 * 
	 * @param driver
	 * @param lastElementToLoad
	 */
	protected void waitForElementLoad(WebDriver driver, String lastElementToLoad, int waitTime) {
		ExpectedCondition<Boolean> pageLoadCondition = waitDocumentReadyObj();
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(pageLoadCondition);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(lastElementToLoad)));
	}

	private static ExpectedCondition<Boolean> waitDocumentReadyObj() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		return pageLoadCondition;
	}

	public void takeSnapshot(WebDriver driver, WebElement elem, String fileWithPath) throws AutomicException {
		Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver,
				elem);
		try {
			ImageIO.write(s.getImage(), "PNG", new File(fileWithPath));
		} catch (IOException e) {

			throw new AutomicException(e.getMessage());
		}

	}
	public void takeSnapshot(WebDriver driver, String fileWithPath) throws AutomicException {
		Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		try {
			ImageIO.write(s.getImage(), "PNG", new File(fileWithPath));
		} catch (IOException e) {

			throw new AutomicException(e.getMessage());
		}

	}

	private void initWebDriver(boolean ignoreSSL, boolean debug) {
		WebDriverManager.chromedriver().setup();

		// Create instance of ChromeOptions Class
		ChromeOptions options = new ChromeOptions();
		// Using the accept insecure cert method with true as parameter to accept the
		// untrusted certificate
		options.setAcceptInsecureCerts(ignoreSSL);
		if (!debug) {
			options.addArguments("--headless");
		}else {
			options.addArguments("start-maximized");
		}

		// Create an object of desired capabilities class with Chrome driver
		DesiredCapabilities SSLCertificate = DesiredCapabilities.chrome();
		// Set the pre defined capability – ACCEPT_SSL_CERTS value to true
		SSLCertificate.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		// Open a new instance of chrome driver with the desired capability
		SSLCertificate.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(SSLCertificate);

		driver = new ChromeDriver(options);
		if(!debug) {
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(2560, 1600));
		}
	}

	protected void setFieldValue(WebElement ele, String value, int spleepValue, boolean isclickToPerform) throws InterruptedException {
		WebDriverWait webDriverWait=	new WebDriverWait(getWebDriver(), 3);
		WebElement webEle = 	webDriverWait.until(ExpectedConditions.elementToBeClickable(ele));
		if(isclickToPerform) {
			webEle.click();
			webEle.click();
		}	
		Thread.sleep(spleepValue);
		ele.sendKeys(value);	
		Thread.sleep(spleepValue);
		
	}
	public abstract void loginAWI() throws AutomicException;

	protected abstract String formAWIDashboardUrl(String dashboard);

}
