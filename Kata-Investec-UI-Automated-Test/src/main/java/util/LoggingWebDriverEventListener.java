package util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

/**
 * @code WebDriver event listener
 */
public class LoggingWebDriverEventListener implements WebDriverEventListener {
    private static final Logger logger = LoggerFactory.getLogger(LoggingWebDriverEventListener.class);

    private By lastFindBy;
    private String originalValue;

    public void afterChangeValueOf(WebElement element, WebDriver driver) {
        String message = String.format(
                "WebDriver changed value in element found by '%s' from '%s' to '%s'",
                getElementLocator(element) /*lastFindBy*/, originalValue, element.getAttribute("value"))
                .toString();
        // Log the message to the logger
        logger.info(message);
        // Log the message to the reporter
        Reporter.log(message);
    }

    public void afterClickOn(WebElement element, WebDriver driver) {
        String message = String.format("WebDriver clicked on element found by '%s'", getElementLocator(element));
        logger.info(message);
        Reporter.log(message);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        String message = String.format("WebDriver found element '%s'", by);
        logger.info(message);
        Reporter.log(message);
    }

    public void afterNavigateBack(WebDriver driver) {
        logger.debug("WebDriver navigated back");
    }

    public void afterNavigateForward(WebDriver driver) {
        logger.debug("WebDriver navigated forward");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {

    }

    public void afterNavigateTo(String url, WebDriver driver) {
        String message = String.format("WebDriver navigated to '%s'", url);
        logger.info(message);
        Reporter.log(message);
    }

    public void afterScript(String script, WebDriver driver) {
        logger.debug("WebDriver run script '{}'", script);
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {

    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        originalValue = element.getAttribute("value");
        logger.trace("WebDriver changing value in element found by '{}' from '{}'",
                getElementLocator(element) /*lastFindBy*/, originalValue);
    }

    public void beforeClickOn(WebElement element, WebDriver driver) {
        logger.trace("WebDriver clicking on element found by '{}'", getElementLocator(element));
    }

    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        lastFindBy = by;
        logger.trace("WebDriver finding element '{}'", by);
    }

    public void beforeNavigateBack(WebDriver driver) {
        logger.trace("WebDriver navigating back from '{}'", driver.getCurrentUrl());
    }

    public void beforeNavigateForward(WebDriver driver) {
        logger.trace("WebDriver navigating forward");
    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {

    }

    public void beforeNavigateTo(String url, WebDriver driver) {
        logger.trace("WebDriver navigating to '{}'", url);
    }

    public void beforeScript(String script, WebDriver driver) {
        logger.trace("WebDriver running script '{}'", script);
    }

    public void onException(Throwable error, WebDriver driver) {
        if (error.getClass().equals(NoSuchElementException.class)){
            logger.error("WebDriver error: Element not found '{}'", lastFindBy);
        } else {
            logger.error("WebDriver error: ", error);
        }
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {

    }

    private String getElementLocator(WebElement element) {
        return element.toString().substring(element.toString().indexOf(">") + 2, element.toString().lastIndexOf("]"));
    }

    private String getElementVisibleText(WebElement element) {
        String visibleText = "unknown";
        String text = element.getText();
        String title = element.getAttribute("title");
        String alt = element.getAttribute("alt");

        if (text != null && !text.isEmpty()) {
            visibleText = text;
        } else if (title != null && !title.isEmpty()) {
            visibleText = title;
        } else if (alt != null && !alt.isEmpty()) {
            visibleText = alt;
        }

        return visibleText;
    }

    private String getElementIdentity(WebElement element) {
        String elementName = "unknown";
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");

        if (id != null && !id.isEmpty()) {
            elementName =  "id: " + id;
        } else if (name != null && !name.isEmpty()) {
            elementName =  "name: " + name;
        }

        return elementName;
    }

}
