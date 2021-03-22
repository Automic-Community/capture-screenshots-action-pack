package com.automic.awi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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
		initWebDriver(inputs.isIgnoreSSL());

	}

	public void loadDashboard(String dashboard) throws AutomicException {
		String dashboardURL = formAWI12DashboardUrl(dashboard);
		String dashboardURLMSG = "Dashboard URL[%s]";
		ConsoleWriter.writeln(String.format(dashboardURLMSG, dashboardURL));
		driver.get(dashboardURL);
		// wait for page until login button is clickable
		waitForelementToBeClickable(driver, Constants.LOGIN, inputs.getTimeOut());
		ConsoleWriter.writeln("Login page loaded ...");
	}

	public void takeDashboardSnapshot(String filePath) throws AutomicException {
		WebElement dashboard = driver.findElement(By.className("uc4-dashboard-layout"));
		takeSnapshot(driver, dashboard, filePath);
	}

	public void takeWidgetSnapshot(String widgetName, String folderPath) throws AutomicException {

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
	public WebDriver getDriver() {
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
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(lastElementToLoad)));
		} catch (TimeoutException e) {
			throw new AutomicException(String.format(ExceptionConstants.PAGE_INPUTS_TIMEOUT, waitTime));
		}
	}

	/**
	 * Check is Element is visible
	 * 
	 * @param driver
	 * @param lastElementToLoad
	 */
	public void waitForElemetLoad(WebDriver driver, String lastElementToLoad, int waitTime) {
		ExpectedCondition<Boolean> pageLoadCondition = waitDocumentReadyObj();
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(pageLoadCondition);
		wait.until(ExpectedConditions.elementToBeClickable(By.className(lastElementToLoad)));
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

	private void initWebDriver(boolean ignoreSSL) {
		WebDriverManager.chromedriver().setup();

		// Create instance of ChromeOptions Class
		ChromeOptions options = new ChromeOptions();
		// Using the accept insecure cert method with true as parameter to accept the
		// untrusted certificate
		options.setAcceptInsecureCerts(ignoreSSL);

		options.addArguments("--headless");

		// Create an object of desired capabilities class with Chrome driver
		DesiredCapabilities SSLCertificate = DesiredCapabilities.chrome();
		// Set the pre defined capability – ACCEPT_SSL_CERTS value to true
		SSLCertificate.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		// Open a new instance of chrome driver with the desired capability
		SSLCertificate.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(SSLCertificate);

		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(2560, 1600));
	}

	public abstract void loginAWI() throws AutomicException;

	protected abstract String formAWI12DashboardUrl(String dashboard);

}
