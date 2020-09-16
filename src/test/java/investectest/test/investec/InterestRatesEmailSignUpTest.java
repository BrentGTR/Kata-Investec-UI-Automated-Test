package investectest.test.investec;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import page.base.TestBase;
import page.investec.HomePage;
import page.investec.InterestRatePage;
import page.investec.SearchResultsPage;
import page.parentpage.Page;

import java.io.IOException;

public class InterestRatesEmailSignUpTest extends TestBase {

    @BeforeMethod
    public void setUp() throws Exception {
        System.out.println("setUp");
        seleniumTestInitialization();
        page = new Page(driver);
    }

    @Test
    public void emailSignUpTest() throws InterruptedException {
        WebDriver driver = getWebDriver();
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        InterestRatePage interestRatePage = new InterestRatePage(driver);

        homePage
                .launchInvestecOnline()
                .searchForInterestRates();

        searchResultsPage
                .clickUnderstandingCashInvestmentInterestRatesLink();

        interestRatePage
                .signUpForInterestRatesEmail("Matthew", "Murdock", "test@investec.co.za")
                .confirmEmailSignUp();
        Reporter.log("Email sign-up was successful");
    }

    @AfterMethod
    public void closeTest(ITestResult result) throws IOException {
        System.out.println(result);
        System.out.println("tearDown");

        if (result.getStatus() == ITestResult.FAILURE) {
            page.takeScreenshotAtEndOfTest(result.getName().trim(),driver);
        }
        driver.quit();
    }
}
