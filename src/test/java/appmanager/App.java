package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

/* ------------------------------------------ Вспомогательные функции ----------------------------------------------*/

public class ApplicationManager {

    private WebDriver driver;

    // (делегирование вместо наследования)
    private DocumentPage documentPage;
    private ValidatorPage validatorPage;
    private MainPage mainPage;

    public final String BASE_PAGE = "http://localhost:8000/auth?canisterId=wzp7w-lyaaa-aaaaa-aaara-cai";

    // Открытие браузера и настройки
    public void init() {

        // Инициализация драйвера
        File file = new File("src/test/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();

        // Неявное ожидание
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Открытие базовой страницы
        driver.navigate().to(BASE_PAGE);

        // объекты классов с методами страниц
        documentPage = new DocumentPage(driver);
        validatorPage = new ValidatorPage(driver);
        mainPage = new MainPage(driver);
    }

    // Логаут и закрытие браузера
    public void stop() {
        mainPage.logout();
        driver.quit();
    }

    public void waitElementIsDisplayed() {
        driver.findElement(By.xpath("//button[@id=\"accept\"]")).isDisplayed();
    }

    public DocumentPage getDocumentPage() {
        return documentPage;
    }

    public ValidatorPage getValidatorPage() {
        return validatorPage;
    }

    public MainPage getMainPage() {
        return mainPage;
    }
}
