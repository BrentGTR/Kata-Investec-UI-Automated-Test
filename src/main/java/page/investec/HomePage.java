package page.investec;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.parentpage.Page;

/**
 * Investec home page
 */
public class HomePage extends Page {

//    @FindBy(id = "toggler-hamburger") private WebElement hamburgerMenu;
    @FindBy(xpath = "//div[contains(@id,'search-toggle')]") private WebElement searchToggler;
    @FindBy(xpath = "//input[contains(@id,'searchBarInput')]") private WebElement searchBarInput;
    @FindBy(id = "searchBarButton") private WebElement searchBarButton;

    /**
     * Constructor injecting the WebDriver interface
     *
     * @param driver
     */
    public HomePage(WebDriver driver) {super(driver);}

    public HomePage launchInvestecOnline(){
        this.getDriver().get("https://www.investec.com/");
        return this;
    }

    public void searchForInterestRates(){
        ngFinish();
        searchToggler.click();
        waitForVisibilityOfNonStaleElement(searchBarInput);
        searchBarInput.sendKeys("Understanding cash investment interest rates");
        searchBarButton.click();
    }


}
