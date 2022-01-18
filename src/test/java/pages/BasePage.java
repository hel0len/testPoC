package pages;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class BasePage extends TestCase {

    public WebDriver driver;

    // ---------------------------------------- Локаторы которые есть везде ------------------------------------------
    public static final String
            BASE_PAGE = "http://localhost:8000/auth?canisterId=wzp7w-lyaaa-aaaaa-aaara-cai";

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        File file = new File("src/test/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
//        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to(BASE_PAGE);
        System.out.println("Вывод сетапе в базовом классе");
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[@id=\"login\"]")).click();
        driver.quit();
    }

    // Логаут после кадого теста
    @AfterMethod
    public void logout() {
        // Выходим из айфрейма
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[@id=\"login\"]")).click();
    }
}
