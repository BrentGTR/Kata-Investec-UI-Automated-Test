package page.base;

import com.google.common.base.CaseFormat;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import page.parentpage.Page;
import util.WebDriverFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver;
    public Properties prop;
    public Page page;

    public static long PAGE_LOAD_TIMEOUT = 180;

    public static  ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();

    public TestBase(){
        try{
            prop =new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/page/config/config.properties");
            prop.load(ip);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void seleniumTestInitialization() {
        webDriverInitialization();
        environmentUrlInitialization();
    }

    public void webDriverInitialization(){
        try{
            driver = WebDriverFactory.create(prop.getProperty("browser"));
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        setWebDriver(driver);

        if(!prop.getProperty("browser").toUpperCase().startsWith("ANDROID")) {
            getWebDriver().manage().window().maximize();
            getWebDriver().manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
            getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        }
        setScriptTimeout(300);
    }

    public void environmentUrlInitialization() {
        page = new Page(getWebDriver());
    }

    @BeforeMethod
    public void displayCurrentTestName(Method method){
        String testName = method.getName();
        System.out.println("\n [ CURRENT TEST: "+CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, testName)+" ]");
        System.out.println("___________________________________________________________________________________________________");
    }

    public  void setScriptTimeout(int timeout) {
        getWebDriver().manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
    }

    public  WebDriver getWebDriver() {
        return dr.get();
    }

    public  void setWebDriver(WebDriver driver) {
        dr.set(driver);
    }

}
