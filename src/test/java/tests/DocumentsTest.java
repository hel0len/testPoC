package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DocumentsTest {

    public WebDriver driver;

    @BeforeTest
    public void setUp() {
        File file = new File("src/test/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to("http://localhost:8000/auth?canisterId=wzp7w-lyaaa-aaaaa-aaara-cai");
        System.out.println("Вывод сетапе в базовом классе");
    }

    @AfterTest
    public void tearDown() {
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

    @Test
    public void firstTest() {
        //setUp();
        //driver.navigate().to("http://localhost:8000/auth?canisterId=wzp7w-lyaaa-aaaaa-aaara-cai");
        driver.findElement(By.xpath("//input[@data-ids=\"validator_employee_1\"]/following-sibling::div")).click();
        driver.findElement(By.xpath("//div[@id=\"regular_1\"]")).click();
        driver.findElement(By.xpath("//img[@alt=\"logo\"]")).click();
        driver.findElement(By.xpath("//li[@id=\"личный кабинет персоны\"]")).click();

        // Переходим на фрейм документов
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")));


        // Инициализируем создание документа
        driver.findElement(By.xpath("//button[@id=\"add-document\"]/span/span")).click();
        driver.findElement(By.xpath("//div[contains(text(), \"MHRus\")]")).click();
        driver.findElement(By.xpath("//li/div[contains(text(), \"Паспорт А\")]")).click();

        // Переходим на фрейм с формой
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rdmx6-jaaaa-aaaaa-aaadq-cai\"]")));

        // Заполняем форму шаблона
        WebElement lastName = driver.findElement(By.xpath("//input[@id=\"lastname\"]"));
        lastName.sendKeys("Test");
        WebElement firstName = driver.findElement(By.xpath("//input[@id=\"firstname\"]"));
        firstName.sendKeys("Name");
        WebElement middleName = driver.findElement(By.xpath("//input[@id=\"middlename\"]"));
        middleName.sendKeys("Middle");
        WebElement birth = driver.findElement(By.xpath("//input[@id=\"birth\"]/../div"));
        birth.click();
        driver.findElement(By.xpath("//abbr[@aria-label=\"17 января 2022 г.\"]")).click();
        WebElement nationality = driver.findElement(By.xpath("//input[@id=\"nationality\"]"));
        nationality.sendKeys("Rus");
        driver.findElement(By.xpath("//input[@type=\"file\"]")).sendKeys("/Users/lenachagina/Projects/TestPoC/src/test/resources/photo_test.jpeg");
        driver.findElement(By.xpath("//button[@id=\"submit\"]")).click();

        // Выходим из айфрейма
        driver.switchTo().defaultContent();
        //Переходим на фрейм документов
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")));

        // Получаем максимальный id заявки
        List<WebElement> docs = driver.findElements(By.xpath("//button/../div/li"));
        int max_id = 0;
        for (WebElement i : docs) {
            int id = Integer.parseInt(i.getAttribute("id"));
            if (id > max_id) {
                max_id = id;
            }
        }
        // Получение локатора последней созданной заявки
        String locator = "//li[@id=\"{SUBSTRING}\"]".replace("{SUBSTRING}", Integer.toString(max_id));

        // Открывам заявку
        driver.findElement(By.xpath(locator)).click();

        // Выходим из айфрейма
        driver.switchTo().defaultContent();
        //Переходим на фрейм документов
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rdmx6-jaaaa-aaaaa-aaadq-cai\"]")));

        // Проверки полей на соответствие данным которые вводили при создании дока
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id=\"lastname\"]/div[2]/span")).getText(), "Test");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id=\"firstname\"]/div[2]/span")).getText(), "Name");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id=\"middlename\"]/div[2]/span")).getText(), "Middle");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id=\"birth\"]/div[2]/span")).getText(), "17-01-2022");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id=\"nationality\"]/div[2]/span")).getText(), "Rus");

        // Переходим на фрейм на котором кнопка
        driver.switchTo().defaultContent();
        //Переходим на фрейм документов
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")))
                .switchTo().
                frame(driver.findElement(By.xpath("//iframe[@id=\"sgymv-uiaaa-aaaaa-aaaia-cai\"]")));

        // Отправить на валидацию
        driver.findElement(By.xpath("//button[@id=\"send-to-validation\"]")).click();

        // Переходим на фрейм на котором поле Статус договора
        driver.switchTo().defaultContent();
        //Переходим на фрейм документов
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")))
                .switchTo().
                frame(driver.findElement(By.xpath("//iframe[@id=\"sgymv-uiaaa-aaaaa-aaaia-cai\"]")));


//        //Проверка статуса заявки в поле "Статус договора"
//        Assert.assertEquals(driver.findElement(By.xpath("//div[@id=\"status\"]//span[contains(text(), \"Статус договора\")]/../following-sibling::div/span")).getText(),
//                "Init");

        // Логаут
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[@id=\"login\"]")).click();

        // Авторизация под валидатором
        driver.findElement(By.xpath("//input[@data-ids=\"validator_employee_1\"]/following-sibling::div")).click();
        driver.findElement(By.xpath("//div[@id=\"validator_employee_1\"]")).click();
        driver.findElement(By.xpath("//img[@alt=\"logo\"]")).click();
        driver.findElement(By.xpath("//li[@id=\"валидатор\"]")).click();

        //Переходим на фрейм со списком заявок
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"qaa6y-5yaaa-aaaaa-aaafa-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tcvdh-niaaa-aaaaa-aaaoa-cai\"]")));

        // Открываем заявку
        // Получение локатора последней созданной заявки
        String locator2 = "//div[contains(text(), \"Заявка на валидацию документа {SUBSTRING}\")]".replace("{SUBSTRING}", Integer.toString(max_id));
        driver.findElement(By.xpath(locator2)).click();

        // Переходим на фрейм с данными заявки
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"sgymv-uiaaa-aaaaa-aaaia-cai\"]")));

        //Кликнуть на "Принять заявку"
        driver.findElement(By.xpath("//button[@id=\"accept\"]")).click();
        // Ждем пока кнопка пропадет
        driver.findElement(By.xpath("//button[@id=\"accept\"]")).isDisplayed();
        //Проверка статуса заявки в поле "Статус договора"
        //Assert.assertEquals(driver.findElement(By.xpath("//div[@id=\"status\"]//span[contains(text(), \"Статус договора\")]/../following-sibling::div/span")).getText(),
        //"Accepted");

        // Логаут
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[@id=\"login\"]")).click();

        // Логин под Регулятор_1
        driver.findElement(By.xpath("//input[@data-ids=\"validator_employee_1\"]/following-sibling::div")).click();
        driver.findElement(By.xpath("//div[@id=\"regular_1\"]")).click();
        driver.findElement(By.xpath("//img[@alt=\"logo\"]")).click();
        driver.findElement(By.xpath("//li[@id=\"личный кабинет персоны\"]")).click();

        //Переходим на фрейм документов
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")));

        // Открывам заявку
        driver.findElement(By.xpath(locator)).click();

        // Выходим из айфрейма
        driver.switchTo().defaultContent();
        //Переходим на фрейм документов
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rdmx6-jaaaa-aaaaa-aaadq-cai\"]")));

        // Отправить на валидацию
        driver.findElement(By.xpath("//button[@id=\"view-validation\"]")).click();
        // Ждем пока кнопка пропадет
        driver.findElement(By.xpath("//button[@id=\"view-validation\"]")).isDisplayed();
        //Проверка статуса заявки в поле "Статус договора"
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id=\"status\"]//span[contains(text(), \"Статус договора\")]/../following-sibling::div/span")).getText(),
                "Init");

    }

}
