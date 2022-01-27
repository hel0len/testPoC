package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/* ------------------------ Вспомогательные методы (сквозные для всех страниц) -------------------------------------*/
public class MainPage {

    public WebDriver driver;
    public Actions actions;
    public WebDriverWait wait;
    public JavascriptExecutor js;

    /* --------------------------------- Локаторы (сквозные для всех страниц )  ------------------------------------ */
    public final String
            USERS_LIST = "//input[@data-ids=\"validator_employee_1\"]/following-sibling::div";
    public final String REGULAR_1_IN_LIST = "//div[@id=\"regular_1\"]";
    public final String VALIDATOR_IN_LIST = "//div[@id=\"validator_employee_1\"]";
    public final String LOGO = "//img[@alt=\"logo\"]";
    public final String PERSONAL_ACCOUNT = "//li[@id=\"личный кабинет персоны\"]";
    public final String VALIDATOR_ACCOUNT = "//li[@id=\"валидатор\"]";
    /* --------------------------------- Локаторы (сквозные для всех страниц )  ------------------------------------ */

    public MainPage(WebDriver driver) {

        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, 20);
        this.js = ((JavascriptExecutor) driver);

    }

    // Ожидание присутствия элемента на странице
    public void waitElementPresents(String locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(locator)));
    }

    // Ожидание исчезновения элемента сос страницы
    public void waitDesapearElement(String locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated
                (By.xpath(locator)));
    }

    // Авторизация под переданным юзером в переданном аккаунте
    public void authByUser(String user_locator, String account_locator) {
        driver.findElement(By.xpath(USERS_LIST)).click();
        driver.findElement(By.xpath(user_locator)).click();
        driver.findElement(By.xpath(LOGO)).click();
        driver.findElement(By.xpath(account_locator)).click();
    }

    // Логаут
    public void logout() {
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[@id=\"login\"]")).click();
    }

    // Клик по переданному локатору
    public void click(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    // Заполнение поля по переданному локатору переданным значением
    public void type(String locator, String text) {
        WebElement lastNameElenent = driver.findElement(By.xpath(locator));
        lastNameElenent.sendKeys(text);
    }

    // Получить текст элемента по переданному локатору
    public String getText(String locator) {
        return driver.findElement(By.xpath(locator)).getText();
    }

    // Ожидание отсутствия элемента на странице с явным указанием таймаута
    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSessions) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSessions);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    // Прокрутка страницы к переданному элементу
    public void scrollToElement(String locator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath(locator)));
    }

    // Скролл страницы вниз
    public void scrollDown() {
        js.executeScript("window.scrollBy(0,2000)");
    }

}
