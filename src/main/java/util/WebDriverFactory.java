package util;

import java.io.FileInputStream;
import io.appium.java_client.android.AndroidDriver;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.*;

/**
 * Factory class to instantiate a WebDriver object.
 *
 * It returns an instance of the driver (local invocation) or an instance of RemoteWebDriver
 */
public class WebDriverFactory {

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static Properties prop;

    public static WebDriver create(String type) throws IllegalAccessException, IOException {
        WebDriver driver;

        System.out.println(System.getProperty("os.name"));

        prop =new Properties();
        FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/page/config/config.properties");
        prop.load(ip);

        if (isUnix() && ("Chrome".equals(type))){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-extensions");
            options.addArguments("--no-sandbox");
            options.addArguments("--ignore-certificate-errors");
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
            System.out.println("Running on Zalenium");
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        }
        else {
            if ("Chrome".equals(type)) {
                WebDriverManager.chromedriver().clearPreferences();
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if  (prop.getProperty("headless").equalsIgnoreCase("true")) {
                    options.addArguments("headless");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--ignore-certificate-errors");
                    options.addArguments("--incognito");
                }
                options.addArguments("--disable-gpu");
                options.addArguments("--dns-prefetch-disable");
                options.addArguments("--disable-extensions");
                options.addArguments("--no-sandbox");
                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                driver = new ChromeDriver(options);
            }
            else if ("IE".equals(type)) {
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.ignoreZoomSettings();
                options.enablePersistentHovering();
                options.introduceFlakinessByIgnoringSecurityDomains();
                options.setCapability("EnableNativeEvents",false);
                options.requireWindowFocus();
                driver = new InternetExplorerDriver(options);
            }
            else if ("Android_Chrome".equals(type)) {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
                capabilities.setCapability("browserName", "Chrome");
                capabilities.setCapability("deviceName",  prop.getProperty("deviceName"));
                capabilities.setCapability("noReset", true);
                //Instantiate Appium Driver
                driver=new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            }
            else if ("Firefox".equals(type) && !isUnix()){
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if  (prop.getProperty("jmeter").equalsIgnoreCase("true")) {
                    String PROXY = "localhost:8888";
                    org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
                    proxy.setHttpProxy(PROXY);
                    proxy.setFtpProxy(PROXY);
                    proxy.setSslProxy(PROXY);
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setAcceptInsecureCerts(true);
                    capabilities.setCapability(org.openqa.selenium.remote.CapabilityType.PROXY, proxy);
                    driver = new org.openqa.selenium.firefox.FirefoxDriver(capabilities);
                }
                else{
                    driver =new FirefoxDriver();
                }
            }
            else if (("Firefox".equals(type)) && isUnix()){
                FirefoxOptions options = new FirefoxOptions();
                options.addPreference("dom.popup_maximum", 0);
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            }
            else {
                throw new IllegalAccessException();
            }
        }
        return driver;
    }

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }
}
