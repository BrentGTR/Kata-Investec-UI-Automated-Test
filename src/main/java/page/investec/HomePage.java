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
    @FindBy(id = "search-toggler__search") private WebElement searchToggler;
    @FindBy(id = "searchBarInput") private WebElement searchBarInput;
    @FindBy(id = "searchBarButton") private WebElement searchBarButton;

    /**
     * Constructor injecting the WebDriver interface
     *
     * @param webDriver
     */
    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    private void launchInvestecOnline(){
        this.webDriver.get("https://www.investec.com/");
    }

    private void searchForInterestRates(){
        searchToggler.click();
        searchBarInput.sendKeys("Interest Rates");
        searchBarButton.click();
    }


}
