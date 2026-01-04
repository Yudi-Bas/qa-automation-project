package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import ui.pages.LoginPage;

public class UiTests {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // FIX: Remove headless untuk debug lokal
        // options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Test login page loads - check title & URL")
    public void testLoginPageLoad() {
        loginPage.open();
        
        
        System.out.println("Page Title: '" + driver.getTitle() + "'");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        
        
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), 
            "Should be on login page");
        Assert.assertTrue(driver.getTitle().length() > 0, 
            "Page should have a title");
    }

    @Test(description = "Test valid login redirects to secure area")
    public void testValidLogin() {
        loginPage.open();
        loginPage.login("tomsmith", "SuperSecretPassword!");
        
        System.out.println("After login URL: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("secure"), 
            "Should redirect to secure area");
    }

    @Test(description = "Test invalid login shows error")
    public void testInvalidLogin() {
        loginPage.open();
        loginPage.login("tomsmith", "wrongpass");
        
        boolean hasError = loginPage.hasErrorMessage();
        System.out.println("Has error message: " + hasError);
        
        Assert.assertTrue(hasError, "Error message should appear");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
