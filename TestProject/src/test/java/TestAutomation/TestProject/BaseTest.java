package TestAutomation.TestProject;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class BaseTest {

	public WebDriver driver;
	public Properties prop;

	public void init() {
		// init the prop file
		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "//projectconfig.properties");
				prop.load(fs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Select browser
	public void openBrowser(String bType) {

		if (bType.equals("Mozilla")) {
			System.setProperty("webdriver.gecko.driver", "C:/Users/AN003SR/Desktop/geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (bType.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_exe"));
			driver = new ChromeDriver();
		} else if (bType.equals("IE")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("iedriver_exe"));
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	/********** Open the test envirnment ***********/
	public void navigate(String urlKey) {

		driver.get(prop.getProperty(urlKey));

	}

	/****** Title of the webpage ******/
	public String getTitle() {

		return (driver.getTitle());

	}

	/*********** Load application **********/
	public void loadApplication(String browser, String urlKey) {

		init();
		openBrowser(browser);
		navigate(urlKey);

	}

	/*********** Verify header of the webpage ***********/
	public String verifyheader(String Header) {
		String header = driver.findElement(By.tagName(prop.getProperty(Header))).getText();
		return (header);
	}

	/************* Verify count header of the webpage.***********/
	public boolean verifyCountheader(String Count1, String pagination1) {
		String count = driver.findElement(By.xpath(prop.getProperty(Count1))).getText();
		int length = count.length();
		String a = count.substring(0, 2);
		int k = Integer.parseInt(a);
		if (count.contains("computers found")) {
			return (true);
		} else {
			return (false);
		}
	}

	/***************************** Validate table heading*********************/
	public boolean validateTableHeading(String tableheadingcompany, String tableheadingdiscontinue,
			String tableheadingintroduce, String tableheadingcomputer) {

		init();
		String h1 = driver.findElement(By.xpath(prop.getProperty(tableheadingcompany))).getText();
		String h2 = driver.findElement(By.xpath(prop.getProperty(tableheadingdiscontinue))).getText();
		String h3 = driver.findElement(By.xpath(prop.getProperty(tableheadingintroduce))).getText();
		String h4 = driver.findElement(By.xpath(prop.getProperty(tableheadingcomputer))).getText();

		if (h1.equals("Company")) {
			if (h2.equals("Discontinued")) {
				if (h3.equals("Introduced")) {
					if (h4.equals("Computer name")) {
						return (true);
					} else {
						return (false);
					}
				} else {
					return (false);
				}
			} else {
				return (false);
			}
		} else {
			return (false);
		}
	}

	/********************* Clicking on next button******************/
	public void nextbutton(int i, String nextbuttonlocator) {

		init();
		for (int j = 1; j <= i; j++)
			driver.findElement(By.xpath(prop.getProperty(nextbuttonlocator))).click();

	}

	/*******************total number of records********************/
	public int totalrecord(String Count1) {

		String count = driver.findElement(By.xpath(prop.getProperty(Count1))).getText();
		int length = count.length();
		String a = new String();
		if (length == 19) {
			a = count.substring(0, 3);
		} else if (length == 18) {
			a = count.substring(0, 2);
		} else if (length == 17) {
			a = count.substring(0, 1);
		}

		int k = Integer.parseInt(a);
		return (k);

	}

	/*************************** Validate pagination********************/
	public boolean validatepagination(String page, String pagination1) {

		init();
		String k = prop.getProperty(page);
		if (k.contains("to")) {
			String pagination = driver.findElement(By.id(prop.getProperty(pagination1))).getText();
			if (pagination.contains(k))
				return (true);
			else {
				return (false);
			}
		} else if (k.contains("/html/body/section/h1")) {
			int recordcount = totalrecord(page);
			String m = Integer.toString(recordcount);
			String pagination = driver.findElement(By.id(prop.getProperty(pagination1))).getText();
			if (pagination.contains(m))
				return (true);
			else {
				return (false);
			}

		}
		return false;
	}

	/************************ Clear the filter box*******************/
	public void clearfilter(String searchbox, String searchsubmit) {

		init();
		driver.findElement(By.id(prop.getProperty(searchbox))).clear();
		driver.findElement(By.id(prop.getProperty(searchsubmit))).click();

	}

	/******************************** Search a computer***********************/
	public void searchComputer(String Search, String searchbox, String searchsubmit) {

		init();
		clearfilter("searchbox", "searchsubmit");
		driver.findElement(By.id(prop.getProperty(searchbox))).sendKeys(prop.getProperty(Search));
		driver.findElement(By.id(prop.getProperty(searchsubmit))).click();

	}

	/***************************** Filter record in the table*****************************/
	public boolean filterrecord(String tablesplit1, String tablesplit2, int record) {

		ArrayList list = new ArrayList();
		init();
		int x = 0;
		String t1 = prop.getProperty(tablesplit1);
		String t2 = prop.getProperty(tablesplit2);
		for (int i = 1; i <= record; i++) {
			String table = t1 + i + t2;
			list.add(driver.findElement(By.xpath(table)).getText());
			String m = (String) list.get(i - 1);

			if (m.toUpperCase().contains("ACE")) {
				x++;
			}
		}

		if (x == record)
			return (true);
		else {
			return (false);
		}

	}

	/**************Search a record which is not present**********************/
	public boolean invalidtext(String nothing) {

		init();
		String nothing1 = driver.findElement(By.xpath(prop.getProperty(nothing))).getText();
		if (nothing1.equals("Nothing to display")) {
			return (true);
		} else {
			return (false);
		}

	}

	/***************************Verify Add computer page************************/
	public boolean newcomputerpage(String Add, String Computername, String Introduced, String Discontinued,
			String Company) {

		init();
		driver.findElement(By.id(prop.getProperty(Add))).click();
		if (driver.findElement(By.id(prop.getProperty(Computername))).isDisplayed()
				&& driver.findElement(By.id(prop.getProperty(Introduced))).isDisplayed()
				&& (driver.findElement(By.id(prop.getProperty(Introduced))).isDisplayed()))
			return (true);
		else
			return (false);

	}

	/********************* Add a new computer in a record**************************/
	public boolean addnewcomputer(String computername1, String introdate, String disdate, String companyname) {

		init();
		driver.findElement(By.id("name")).sendKeys(prop.getProperty(computername1));
		driver.findElement(By.id("introduced")).sendKeys(prop.getProperty(introdate));
		driver.findElement(By.id("discontinued")).sendKeys(prop.getProperty(disdate));
		Select dropdown = new Select(driver.findElement(By.id("company")));
		dropdown.selectByVisibleText(prop.getProperty(companyname));
		driver.findElement(By.xpath("/html/body/section/form/div/input")).click();
		String done = driver.findElement(By.xpath("/html/body/section/div[1]")).getText();
		String h = "Done! Computer " + prop.getProperty(computername1) + " has been created";
		if (done.equals(h))
			return (true);
		else
			return (false);

	}

	/*************************** On clicking cancel button it will redirect to list page********************/
	public boolean cancelbutton(String Add, String Cancel) {

		init();
		driver.findElement(By.id(prop.getProperty(Add))).click();
		driver.findElement(By.xpath(prop.getProperty(Cancel))).click();
		if (driver.findElement(By.id(prop.getProperty(Add))).isDisplayed())
			return true;
		else
			return false;

	}

	/**************************************** Create a new computer record************************************/
	public boolean mandatoryfields(String computername2) {

		init();
		driver.findElement(By.id("add")).click();
		driver.findElement(By.id("name")).sendKeys(prop.getProperty(computername2));
		driver.findElement(By.xpath("/html/body/section/form/div/input")).click();
		String done = driver.findElement(By.xpath("/html/body/section/div[1]")).getText();
		String h = "Done! Computer " + prop.getProperty(computername2) + " has been created";
		if (done.equals(h))
			return (true);
		else
			return (false);

	}

	/*********************** Check validation Computer field**********************************/
	public boolean blank(String Add, String Create) {

		init();
		driver.findElement(By.id(prop.getProperty(Add))).click();
		driver.findElement(By.xpath(prop.getProperty(Create))).click();
		String done = driver.findElement(By.xpath("/html/body/section/form/fieldset/div[1]/div/span")).getText();

		if (done.equals("Required"))
			return (true);
		else
			return (false);

	}

	
}
