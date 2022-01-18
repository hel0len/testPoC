package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage {

    protected WebDriver driver;

    public AuthPage(WebDriver driver) {
        this.driver = driver;
    }

    // -------------------------------- Локаторы элементов на странице авторизации ------------------------------------
    public final String
            USERS_LIST = "//input[@data-ids=\"validator_employee_1\"]/following-sibling::div",
            REGULAR_1_IN_LIST = "//div[@id=\"regular_1\"]",
            LOGO = "//img[@alt=\"logo\"]",
            PERSONAL_ACCOUNT = "//li[@id=\"личный кабинет персоны\"]";

    // Авторизация под переданным пользователем
    public void authByUser(String user_name) {
        driver.findElement(By.xpath(USERS_LIST)).click();
        driver.findElement(By.xpath(user_name)).click();
        driver.findElement(By.xpath(LOGO)).click();
        driver.findElement(By.xpath("//li[@id=\"личный кабинет персоны\"]")).click();
    }
}
