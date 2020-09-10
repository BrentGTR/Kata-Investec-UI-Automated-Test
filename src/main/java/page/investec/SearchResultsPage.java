package page.investec;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.parentpage.Page;

public class SearchResultsPage extends Page {
    @FindBy(xpath = "//h4[contains(.,'Understanding cash investment interest rates')]")
    WebElement understandingCashInvestmentInterestRatesLink;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void clickUnderstandingCashInvestmentInterestRatesLink(){
        waitForVisibilityOfNonStaleElement(understandingCashInvestmentInterestRatesLink);
        understandingCashInvestmentInterestRatesLink.click();
    }
}
