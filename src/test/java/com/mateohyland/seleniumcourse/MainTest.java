package com.mateohyland.seleniumcourse;

import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mateohyland.seleniumcourse.pageobjects.HomePage;
import com.mateohyland.seleniumcourse.pageobjects.ResultsPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MainTest {

	private static WebDriverManager manager;
	protected final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	@BeforeClass
	public static void beforeAll() {
		manager = WebDriverManager.getInstance("chrome");
		manager.setup();
	}

	@BeforeMethod
	public void before() {
		driver.set(manager.create());
	}

	@AfterMethod
	public void after() {
		driver.get().quit();
	}

	@Test
	public void test() {

		HomePage homePage = new HomePage(driver.get()).go();
		
		driver.get().manage().window().maximize();

		homePage.waitAndCloseGeniusPopup();
		
		homePage.searchBox.selectDestination("Camboriú");

		LocalDate today = LocalDate.now();
		LocalDate departureDate = today.plusDays(180);
		LocalDate returnDate = departureDate.plusDays(31);

		ResultsPage resultsPage = homePage.searchBox.selectDestination("Camboriú").selectDates(departureDate, returnDate)
				.submitSelection();

		resultsPage.assertDestinationFound();
		
		resultsPage.searchBox.clearDestinationInput();
		
		resultsPage.searchBox.selectDestination("París");

		LocalDate departureDate2 = today.plusDays(190);
		LocalDate returnDate2 = departureDate.plusDays(33);

		ResultsPage resultsPage2 = resultsPage.searchBox.selectDestination("París").toggleCalendar().selectDates(departureDate2, returnDate2)
				.submitSelection();

		resultsPage2.assertDestinationFound();
	}

	@Test
	public void test2() throws InterruptedException {
		driver.get().get("https://www.despegar.com.ar/");
		driver.get().findElement(By.cssSelector(".sbox-places-origin-override input")).sendKeys("Buenos Aires");
		Thread.sleep(10000);
	}

}
