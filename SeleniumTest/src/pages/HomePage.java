package Pages;

import Custom.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;



public class HomePage {


    private final String homePageUrl = "https://robotsparebinindustries.com";


    private By userNameTextField = By.id("username");

    private By passwordTextField = By.id("password");

    private By loginButton = By.tagName("button");
    private By homePageHeaderText = By.tagName("h4");
    private By errorMessage = By.xpath("//div[contains(text(),'Invalid username or password.')]");

    Waits waits = new Waits();

    public String getHomePageUrl(WebDriver driver){
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        return homePageUrl;
    }


    public String getHomePageHeaderText(WebDriver driver){
        waits.customWait(driver, Duration.ofSeconds(40), "presenceOfElement", homePageHeaderText);
        return driver.findElement(homePageHeaderText).getText();
    }
    public String getErrorMessageText(WebDriver driver){
        waits.customWait(driver, Duration.ofSeconds(20), "presenceOfElement", errorMessage);
        return driver.findElement(errorMessage).getText();
    }

    public void fillTheLoginForm(WebDriver driver, String arg1, String arg2){
        waits.customWait(driver, Duration.ofSeconds(20), "presenceOfElement", userNameTextField);
        driver.findElement(userNameTextField).sendKeys(arg1);
        waits.customWait(driver, Duration.ofSeconds(20), "presenceOfElement", passwordTextField);
        driver.findElement(passwordTextField).sendKeys(arg2);
        waits.customWait(driver, Duration.ofSeconds(20), "elementToBeClickable", loginButton);
        driver.findElement(loginButton).click();
    }
}
