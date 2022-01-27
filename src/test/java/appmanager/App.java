package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class App {

    private WebDriver driver;
    public DocumentPage documentPage;
    public ValidatorPage validatorPage;
    public MainPage mainPage;
    public WebDriverWait wait;

    public final String BASE_PAGE = "http://localhost:8000/auth?canisterId=wzp7w-lyaaa-aaaaa-aaara-cai";

    // Открытие браузера и настройки
    @BeforeTest
    public void init() {

        // Инициализация драйвера
        File file = new File("src/test/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();

        // Неявные ожидания
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // ждем загрузку страницы
        driver.manage().timeouts().pageLoadTimeout(1000,
                TimeUnit.MILLISECONDS);
        // Ждем отработку сриптов
        driver.manage().timeouts().setScriptTimeout(1000,
                TimeUnit.MILLISECONDS);


        // Открытие базовой страницы
        driver.navigate().to(BASE_PAGE);

        // объекты классов с методами страниц
        documentPage = new DocumentPage(driver);
        validatorPage = new ValidatorPage(driver);
        mainPage = new MainPage(driver);
        wait = new WebDriverWait(driver, 20);
    }

    // Логаут и закрытие браузера
    @AfterTest
    public void stop() {
        mainPage.logout();
        driver.quit();
    }

}
