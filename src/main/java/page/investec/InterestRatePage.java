package page.investec;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import page.parentpage.Page;

import java.util.concurrent.TimeUnit;

public class InterestRatePage extends Page {

    @FindBy(xpath = "//button[contains(text(),'Sign up')]")
    WebElement signUpButton;
    @FindBy(xpath = "(//input[contains(@class,'text-input__input')])[1]") WebElement firstNameInput;
    @FindBy(xpath = "(//input[contains(@class,'text-input__input')])[2]") WebElement lastNameInput;
    @FindBy(xpath = "(//input[contains(@class,'text-input__input')])[3]") WebElement emailInput;
    @FindBy(xpath = "//button[contains(.,'Myself')]") WebElement myselfCheckbox;
    @FindBy(xpath = "//button[contains(text(),'Submit')]") WebElement submitButton;
    @FindBy(xpath = "//h3[contains(text(),'Thank you')]") WebElement thankYouHeader;
    @FindBy(xpath = "//h4[contains(text(),'We look forward to sharing')]") WebElement emailSignUpConfirmationText;

    /**
     * Constructor injecting the WebDriver interface
     *
     * @param driver
     */
    public InterestRatePage(WebDriver driver) {
        super(driver);
    }

    public InterestRatePage signUpForInterestRatesEmail(String firstName, String lastName, String email) throws InterruptedException {
        waitForVisibilityOfNonStaleElement(signUpButton);
        signUpButton.click();
        waitForVisibilityOfNonStaleElement(firstNameInput);
        ngSendKeys(firstNameInput, firstName);
        lastNameInput.sendKeys(lastName);
        emailInput.sendKeys(email);
        myselfCheckbox.click();
        submitButton.click();
        return this;
    }

    public void confirmEmailSignUp(){
        waitForVisibilityOfNonStaleElement(thankYouHeader);
        Assert.assertTrue(emailSignUpConfirmationText.isDisplayed(), "email sign up confirmation is not displayed");
    }
}
