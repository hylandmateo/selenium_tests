package com.mateohyland.seleniumcourse.pageobjects;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {

	protected static final int CLEAR_INPUT_TIMEOUT = 3;
	protected static final int DATES_CONTAINER_TIMEOUT = 3;

	protected WebDriver driver;
	protected String destination;
	
	public final SearchBox searchBox;
	
	@FindBy(css="[data-component=arp-header]")
	protected WebElement resultsHeader;
	
	@FindBy(css="[data-testid=input-clear]")
	protected WebElement clearInput;
	
	@FindBy(css="[data-testid=searchbox-dates-container]")
	protected WebElement datesContainer;
	
	public ResultsPage (WebDriver driver, String destination) {
		this.driver = driver;
		this.searchBox = new SearchBox(driver);
		this.destination = destination;
		
		PageFactory.initElements(driver, this);
	}
	
	public void clearInput() {
		WebDriverWait waitForClearInput = new WebDriverWait(driver, Duration.ofSeconds(CLEAR_INPUT_TIMEOUT));
		waitForClearInput.until(ExpectedConditions.elementToBeClickable(clearInput)).click();
		
		WebDriverWait waitForDatesContainer = new WebDriverWait(driver, Duration.ofSeconds(DATES_CONTAINER_TIMEOUT));
		waitForDatesContainer.until(ExpectedConditions.elementToBeClickable(datesContainer)).click();
	}
	
	public void assertDestinationFound() {
		assertTrue(resultsHeader.getText().contains(destination));
		assertTrue(driver.getTitle().contains(destination));
	}
}
