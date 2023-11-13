package Tests;

import Pages.ElementsPage;
import Pages.HomePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestPages {

    WebDriver driver;

    HomePage homePage;

    ElementsPage elementsPage;


    @Before
    public void loadHomePage() throws InterruptedException {
        homePage = new HomePage();
        elementsPage = new ElementsPage();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(homePage.getHomePageUrl());
        Thread.sleep(1000);
    }

    @Test
    public void testTheHomePageUrl() {
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = homePage.getHomePageUrl() + "/";
        Assert.assertEquals("[ERROR] Different than the expected URL!", expectedUrl, currentUrl);
    }

    @Test
    public void checkHomePageHeaderText() {
        Assert.assertTrue(homePage.getHomePageHeaderText(driver).contains("Hey! Wait a minute... What is this site?"));
    }

    @Test
    public void successfulLogin() {
        homePage.fillTheLoginForm(driver);
        Assert.assertTrue(elementsPage.isLogOutButtonDisplayed(driver));
    }

    @Test
    public void successfulLogOut() {
        homePage.fillTheLoginForm(driver);
        Assert.assertTrue(elementsPage.isLogOutButtonDisplayed(driver));
        elementsPage.clickOnLogout(driver);
        Assert.assertTrue(homePage.getHomePageHeaderText(driver).contains("Hey! Wait a minute... What is this site?"));
    }

    @Test
    public void testSalesFillingForm() {
        homePage.fillTheLoginForm(driver);
        elementsPage.fillSalesForm(driver, "Angel", "Angelov", "5000", "7000");
        elementsPage.clickOnSubmit(driver);
        String expectedName = "Angel Angelov";
        Assert.assertEquals("[ERROR] Different than the expected Name!"
                , expectedName, elementsPage.getTableSummaryName(driver));
        Assert.assertTrue(elementsPage.getActiveSalesPeopleCount() > 0);
        Assert.assertEquals((elementsPage.getActivePeopleSalesResult(driver))
                , "Active sales people:" + "\n" + elementsPage.getActiveSalesPeopleCount().toString());

    }

    @Test
    public void testCalculationSalesForm() {
        homePage.fillTheLoginForm(driver);
        elementsPage.fillSalesForm(driver, "Petar", "Petrov", "5000", "8000");
        elementsPage.clickOnSubmit(driver);
        Assert.assertEquals((elementsPage.getActivePeopleSalesResult(driver))
                , "Active sales people:" + "\n" + elementsPage.getActiveSalesPeopleCount().toString());
        Assert.assertEquals((elementsPage.getExpectedSalesResult(driver))
                , "Expected sales:" + "\n" + "$5,000");
        Assert.assertEquals((elementsPage.getActualSalesResult(driver))
                , "Actual sales:" + "\n" + "$8,000");
        Assert.assertEquals((elementsPage.getDifferenceResult(driver))
                , "Difference:" + "\n" + "$3,000");

    }

    @Test
    public void testPerformanceButtonFunctionality() {
        homePage.fillTheLoginForm(driver);
        elementsPage.fillSalesForm(driver, "Anton", "Angelov", "10000", "15000");
        elementsPage.clickOnSubmit(driver);
        Assert.assertTrue(elementsPage.isPerformanceButtonDisplayed(driver));
        elementsPage.clickOnPerformanceButton(driver);
        String expectedMessage = "A positive result. Well done!";
        Assert.assertEquals(expectedMessage, elementsPage.getPerformanceMessage(driver));

    }

    @Test
    public void testDeleteButtonFunctionality() {
        homePage.fillTheLoginForm(driver);
        elementsPage.fillSalesForm(driver, "Victor", "Ivanov", "10000", "15000");
        elementsPage.clickOnSubmit(driver);
        elementsPage.clickOnDeleteButton(driver);
        Assert.assertTrue(elementsPage.getFirstName(driver).isEmpty());
        Assert.assertTrue(elementsPage.getLastName(driver).isEmpty());
        Assert.assertTrue(elementsPage.getSalesResult(driver).isEmpty());


    }

    @After
    public void quitDriver() {
        driver.quit();
    }
}