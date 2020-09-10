package page.parentpage;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Page {
    private Properties prop;
    private final WebDriver driver;

    public Page(WebDriver driver) {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/page/config/config.properties");
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public Properties getProp() {
        return prop;
    }

    public void ngFinish() {
        Instant start = Instant.now();
        try{
            new NgWebDriver((JavascriptExecutor) getDriver()).waitForAngularRequestsToFinish();

            Set<String> errorStrings = new HashSet<>();
            errorStrings.add("SyntaxError");
            errorStrings.add("EvalError");
            errorStrings.add("ReferenceError");
            errorStrings.add("RangeError");
            errorStrings.add("TypeError");
            errorStrings.add("URIError");
            errorStrings.add("Error");
            errorStrings.add("AggregateError");

            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);

            for (LogEntry entry : logs) {
                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);
                System.out.println(entry.toString() + "and this call took: "+ timeElapsed.toMillis() +" milliseconds");
                for (String error : errorStrings) {
                    if (entry.getMessage().contains(error))
                        System.out.println(entry.getLevel() + " Console error message: " +  entry.getMessage());
                }
            }

            logs = driver.manage().logs().get("performance");
            int status = -1;
            for (LogEntry entry : logs) {
                try {
                    JSONObject json = new JSONObject(entry.getMessage());
                    JSONObject message = json.getJSONObject("message");
                    String method = message.getString("method");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }catch (Exception e){
            System.out.print("A NgWebDriver.waitForAngularRequestsToFinish() timeout has occurred.");
        }
    }

    public void takeScreenshotAtEndOfTest(String testName, WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + testName + ".png"));
    }

    public void waitForVisibilityOfNonStaleElement(WebElement locator) {
        Boolean staleElement = true;
        int retries = 0;
        while (staleElement && retries<5) {
            try {
                System.out.println("Waiting to see "+locator + "; try: "+ retries+1);
                new WebDriverWait(getDriver(), 90).until(ExpectedConditions.visibilityOf(locator));
                staleElement = false;
                retries++;

            } catch (StaleElementReferenceException e) {
                staleElement = true;
            }
        }
    }

    public void ngHover(WebElement element) throws InterruptedException {
        new WebDriverWait(driver, 30).pollingEvery(Duration.ofMillis(100)).until(ExpectedConditions.visibilityOf(element));
        new WebDriverWait(driver, 30).pollingEvery(Duration.ofMillis(100)).until(ExpectedConditions.elementToBeClickable(element));
        ngFinish();
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public void waitClearAndSendKeys(WebElement element, String text) throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(1))
                .pollingEvery(Duration.ofMillis(100));

        int count = 0;

        while (true) {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.clear();
                element.sendKeys(text);
                break;
            } catch (WebDriverException e) {
                Thread.sleep(1000);
                if (++count == 60) throw e;
            }
        }
    }

    public void ngSendKeys(WebElement element, String text) throws InterruptedException {
        ngFinish();
        ngHover(element);
        waitClearAndSendKeys(element, text);
    }
}