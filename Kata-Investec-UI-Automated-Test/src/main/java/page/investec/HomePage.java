package page.investec;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import page.Page;

/**
 * Google search page
 */
public class HomePage extends Page {

    // The element is looked up using the name attribute
    @FindBy(id = "toggler-hamburger") private WebElement hamburgerMenu;

    /**
     * Constructor injecting the WebDriver interface
     *
     * @param webDriver
     */
    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }



}
