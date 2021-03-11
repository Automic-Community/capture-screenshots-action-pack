package analytics;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * WebsiteCrawl
 */
public class CrawlerV12 {

	private WebDriver driver;
	JavascriptExecutor js;

	public CrawlerV12() throws Exception {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("start-maximized");
		//options.addArguments("enable-automation");
		//options.addArguments("--no-sandbox");
		// options.addArguments("--disable-infobars");
		// options.addArguments("--disable-dev-shm-usage");
		//options.addArguments("--disable-browser-side-navigation");
		//options.addArguments("--disable-gpu");
		 options.addArguments("--headless");
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(2560, 1600));
		//js=(JavascriptExecutor)driver;
		
		//enablejQuery();
		// driver.get(url);

	}

	public void crawl(Environment env) {
		try {
			// Navigate to the specified directory
			driver.get(env.getDashboadURL());
			
			System.out.println("Waiting for login page..");
			// Sleep for 5 seconds in case the website has not fully loaded
			//Thread.sleep(2000);
			//Login AWI
			login(env);
			 
			takeDashboardSnapshot(env);
			
			for(String widget: env.getWidgets()) {				
				takeWidgetSnapshot(env, widget.trim());
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		// Close after completion
		driver.close();
	}

	

	public void login(Environment env) throws Exception {		
		//Client
		driver.findElement(By.xpath("//*[@id=\"awi-97011\"]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[5]/div/div/input")).sendKeys(env.getClient());
		driver.findElement(By.xpath("//*[@id=\"awi-97011\"]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[5]/div/div/input")).click();
		Thread.sleep(500);
		
		//Username
		driver.findElement(By.xpath("//*[@id=\"awi-97011\"]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[7]/div/input")).sendKeys(env.getUser());
		driver.findElement(By.xpath("//*[@id=\"awi-97011\"]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[7]/div/input")).click();
		Thread.sleep(500);
		
		//Department
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[9]/div/input")).sendKeys(env.getDepartment());
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[9]/div/input")).click();
		Thread.sleep(500);
		
		//password
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[11]/div/div/div/input")).sendKeys(env.getPassword());
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[1]/div/div[3]/div/div[11]/div/div/div/input")).click();
		Thread.sleep(100);

		//password
		driver.findElement(By.xpath("//*[@id=\"awi-97011\"]/div/div[2]/div/div/div/div[1]/div/div[6]/div/div[5]/div/div/div/div")).click();
		Thread.sleep(500);
		System.out.println("Login suceessfully....");
		// Dashboard Navigation

		System.out.println("Opening Analytics Dashboard...");
		Thread.sleep(500);
		System.out.println("waiting 10S before taking screenshot...");
		//Thread.sleep(10000);



	}
	
	public void takeDashboardSnapshot(Environment env) {
		WebElement dashboard = 	driver.findElement(By.className("uc4-dashboard-layout"));
		takeSnapshot(driver, dashboard, env.getFolderPath()+"dashboardScreenshot.png");		
	}
	
	public void takeWidgetSnapshot(Environment env, String widgetName){
		
		List<WebElement> widgets= driver.findElements(By.className("v-gridlayout-slot"));
		
		for (WebElement webElement : widgets) {		
			
				WebElement t=webElement.findElement(By.className("uc4-dashboard-widget-header-text"));
				String name = t.getText();
				if(widgetName.equals(name)) {
					takeSnapshot(driver, webElement, env.getFolderPath()+name+".png");			    
				}			
		}		
	}
	
	private void takeSnapshot(WebDriver driver, WebElement elem, String fileWithPath) {
		Screenshot s=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot( driver, elem);
	    try {
			ImageIO.write(s.getImage(),"PNG",new File(fileWithPath));
		} catch (IOException e) {					
			e.printStackTrace();
		}
		
	}

	public String formAWI21DashboardUrl(Environment env) {

		return env.getAwiUrl() + env.getAeConnection() + "/0" + env.getClient() + "/@home/dashboards/"
				+ env.getDashboard();
	}
	public String formAWI12DashboardUrl(Environment env) {

		return env.getAwiUrl()+"#" + env.getAeConnection() + "/0" + env.getClient() + "/@home/dashboards/"
				+ env.getDashboard();
	}
	
	public void putValuesUsingJavaScriptV21(Environment env) throws Exception {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String client = "window.jQuery('ecc-form-field[label=\"Client\"] ecc-spinner').val('" + env.getClient() + "')";
		String name = "window.jQuery('ecc-form-field[label=\"Name\"] ecc-textfield').val('" + env.getUser() + "')";
		String department = "window.jQuery('ecc-form-field[label=\"Department\"] ecc-textfield').val('"
				+ env.getDepartment() + "')";
		String password = "window.jQuery('ecc-form-field[label=\"Password\"] ecc-passwordfield').val('"
				+ env.getPassword() + "')";
		String submit = "window.jQuery('ecc-button[caption=\"I understand. Log in anyway.\"]').click()";

		Thread.sleep(2000);
		executor.executeScript(client);
		executor.executeScript(name);
		executor.executeScript(department);
		executor.executeScript(password);
		Thread.sleep(1000);
		executor.executeScript(submit);
		Thread.sleep(10000);
		System.out.println("Login suceessfully....");
		Thread.sleep(10000);
		System.out.println("Analytics Dashboard opened..");

	}

}