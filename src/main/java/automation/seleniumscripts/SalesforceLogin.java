package automation.seleniumscripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SalesforceLogin {
    public static void main(String[] args) {

        // Set up the WebDriver (Ensure you have the correct WebDriver installed)
        WebDriver driver = new ChromeDriver();

        // Open Salesforce Login Page
        driver.get("https://login.salesforce.com/");

        // Maximize window
        driver.manage().window().maximize();

        // Locate Username field and enter value
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("your_username");

        // Locate Password field and enter value
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("your_password");

        // Locate Remember Me checkbox and select it (if needed)
        WebElement rememberMe = driver.findElement(By.id("rememberUn"));
        if (!rememberMe.isSelected()) {
            rememberMe.click();
        }

        // Locate and click Login button
        WebElement loginButton = driver.findElement(By.id("Login"));
        loginButton.click();

        // Wait for the page to load (optional: use explicit waits if needed)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the title of the new page after login
        System.out.println("Page Title: " + driver.getTitle());

        // Close browser
        driver.quit();
    }
}
