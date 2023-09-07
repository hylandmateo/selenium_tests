package com.mateohyland.seleniumcourse.pageobjects;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultsPage {

	protected WebDriver driver;
	protected String destination;
	
	@FindBy(css="[data-component=arp-header]")
	protected WebElement resultsHeader;
	
	public ResultsPage (WebDriver driver, String destination) {
		this.driver = driver;
		this.destination = destination;
		
		PageFactory.initElements(driver, this);
	}
	
	public void assertDestinationFound() {
		assertTrue(resultsHeader.getText().contains(destination));
		assertTrue(driver.getTitle().contains(destination));
	}
}
