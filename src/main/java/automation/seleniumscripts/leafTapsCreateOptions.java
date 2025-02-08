package automation.seleniumscripts;

    import org.openqa.selenium.By;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.WebElement;
    import org.openqa.selenium.firefox.FirefoxDriver;

public class leafTapsCreateOptions {
        public static void main(String[] args) {
            // Initialize Firefox driver
            System.setProperty("webdriver.gecko.driver", "/your/path/geckodriver");
            WebDriver driver = new FirefoxDriver();
            try {
                // Navigate to your application
                driver.get("your_application_url");
                // Locate and click on \"Create Lead\" link
                WebElement createLeadLink = driver.findElement(By.id("ext-gen62"));
                createLeadLink.click();
                // Wait for the page to load (optional)
                Thread.sleep(2000);
                // Locate and click on \"Create Account\" link
                WebElement createAccountLink = driver.findElement(By.id("ext-gen63"));
                createAccountLink.click();
                // Locate and click on \"Create Order\" link
                WebElement createOrderLink = driver.findElement(By.id("ext-gen64"));
                createOrderLink.click();
                // Verify page title
                String pageTitle = driver.getTitle();
                System.out.println("Page Title: " + pageTitle);
                // Close the browser
                driver.quit();
            } catch (Exception e) {
                e.printStackTrace();
                driver.quit();
            }
        }

    }

