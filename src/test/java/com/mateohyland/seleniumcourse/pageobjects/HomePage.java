package com.mateohyland.seleniumcourse.pageobjects;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	protected static final int GENIUS_POPUP_TIMEOUT = 3;
	protected static final int DESTINATION_SELECTION_TIMEOUT = 3;
	protected static final int CALENDAR_POPUP_TIMEOUT = 3;
	protected static final int SUBMIT_BUTTON_TIMEOUT = 3;

	protected static final String URL = "https://www.booking.com/index.es-ar.html";

	protected WebDriver driver;

	protected String currentDestination;
	
	@FindBy(css = "[role='dialog'][aria-modal='true'] button")
	protected WebElement geniusPopup;

	@FindBy(id = ":re:")
	protected WebElement destinationInput;

	@FindBy(css = "[data-testid='searchbox-datepicker']")
	protected WebElement calendar;
	
	@FindBy(css = "[data-testid='searchbox-datepicker-calendar'] button:last-of-type")
	protected WebElement calendarForwardArrow;

	@FindBy(css = "[type='submit']")
	protected WebElement submitButton;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public HomePage go() {
		driver.get(URL);
		return this;
	}

	public void waitAndCloseGeniusPopup() {
		WebDriverWait geniusPopupWait = new WebDriverWait(driver, Duration.ofSeconds(GENIUS_POPUP_TIMEOUT));
		geniusPopupWait.until(ExpectedConditions.elementToBeClickable(geniusPopup)).click();
	}

	public HomePage selectDestination(String destination) {

		destinationInput.sendKeys(destination);

		WebDriverWait destinationFirstChoiceWait = new WebDriverWait(driver,
				Duration.ofSeconds(DESTINATION_SELECTION_TIMEOUT));
		destinationFirstChoiceWait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//ul[@data-testid='autocomplete-results-options']//*[contains(text(),'" + destination + "')]")))
				.click();
		
		this.currentDestination = destination;
		
		return this;
	}

	public HomePage selectDates(LocalDate departureDate, LocalDate returnDate) {
		
		List<WebElement> departureCells;
		List<WebElement> returnCells;
		
		WebDriverWait calendarWait = new WebDriverWait(driver, Duration.ofSeconds(CALENDAR_POPUP_TIMEOUT));
		calendarWait.until(ExpectedConditions.visibilityOf(calendar));
		
		while((departureCells = driver.findElements(By.cssSelector("[data-date='" + departureDate + "']"))).isEmpty()) {
			calendarForwardArrow.click();
		}
		departureCells.get(0).click();
		
		while((returnCells = driver.findElements(By.cssSelector("[data-date='" + returnDate + "']"))).isEmpty()) {
			calendarForwardArrow.click();
		}
		returnCells.get(0).click();
		
		return this;
	}
	
	public ResultsPage submitSelection() {
		WebDriverWait submitButtonWait = new WebDriverWait(driver, Duration.ofSeconds(SUBMIT_BUTTON_TIMEOUT));
		submitButtonWait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
		
		return new ResultsPage(driver, currentDestination);
	}

}
