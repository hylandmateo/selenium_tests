package com.mateohyland.seleniumcourse.pageobjects;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	protected static final int GENIUS_POPUP_TIMEOUT = 3;

	protected static final String URL = "https://www.booking.com/index.es-ar.html";

	protected WebDriver driver;
	
	@FindBy(css = "[role='dialog'][aria-modal='true'] button")
	protected WebElement geniusPopupCloseButton;

	public final SearchBox searchBox;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.searchBox = new SearchBox(driver);
		PageFactory.initElements(driver, this);
	}

	public HomePage go() {
		driver.get(URL);
		return this;
	}

	public void waitAndCloseGeniusPopup() {
		WebDriverWait geniusPopupWait = new WebDriverWait(driver, Duration.ofSeconds(GENIUS_POPUP_TIMEOUT));
		geniusPopupWait.until(ExpectedConditions.elementToBeClickable(geniusPopupCloseButton)).click();
	}
}
