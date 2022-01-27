package appmanager;

import model.DocumentData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


/* ------------------------------------------ Методы в аккаунте Валидатора -----------------------------------------*/
public class ValidatorPage extends MainPage {

    public final String
            APPLICATION_IN_LIST_BY_ID = "//div[contains(text(), \"Заявка на валидацию документа {SUBSTRING}\")]",
            STATUS_APPLICATION_IN_LIST_BY_ID = "//div[contains(text(), \"Заявка на валидацию документа {SUBSTRING}\")]/../div[2]",
            STATUS_CONTRACT_VALUE = "//span[contains(text(), \"Статус договора\")]/../following-sibling::div/span",
            LASTNAME_VALUE = "//div[@id=\"lastname\"]/div[2]/span",
            FIRSTNAME_VALUE = "//div[@id=\"firstname\"]/div[2]/span",
            MIDDLENAME_VALUE = "//div[@id=\"middlename\"]/div[2]/span",
            BIRTH_VALUE = "//div[@id=\"birth\"]/div[2]/span",
            NATIONALITY_VALUE = "//div[@id=\"nationality\"]/div[2]/span",
            ACCEPTED_STATUS = "//span[contains(text(), \"Статус договора\")]/../following-sibling::div/span[contains(text(), \"Accepted\")]",
            APPROVE_BTN = "//button[@data-variant=\"contained\"]",
            ACCEPT_BTN = "//button[@id=\"accept\"]";


    public ValidatorPage(WebDriver driver) {
        super(driver);
    }

    //Кликнуть на "Принять заявку"
    public void acceptClick() {
        switchToFrameDocumentsStatus();
        click(ACCEPT_BTN);
    }

    // Переходим на фрейм с данными заявки
    public void switchToFrameApplicationData() {
        switchToFrameDocumentsStatus();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id=\"sgymv-uiaaa-aaaaa-aaaia-cai\"]")));
    }

    // Открываем заявку по переданному id
    public void openApplicationById(int max_id) {
        switchToFrameDocumentsList();
        String locator = APPLICATION_IN_LIST_BY_ID.replace("{SUBSTRING}", Integer.toString(max_id));
        click(locator);
    }

    // Переходим на фрейм со списокм заявок
    public void switchToFrameDocumentsList() {
        driver.switchTo().defaultContent();
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"qaa6y-5yaaa-aaaaa-aaafa-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tcvdh-niaaa-aaaaa-aaaoa-cai\"]")));
    }

    // Переходим на фрейм со статусами по заявке
    public void switchToFrameDocumentsStatus() {
        driver.switchTo().defaultContent();
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"qaa6y-5yaaa-aaaaa-aaafa-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tcvdh-niaaa-aaaaa-aaaoa-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"sgymv-uiaaa-aaaaa-aaaia-cai\"]")));
    }

    // Переходим на фрейм с формой
    public void switchToFrameApplicationForm() {
        driver.switchTo().defaultContent();
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"qaa6y-5yaaa-aaaaa-aaafa-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tcvdh-niaaa-aaaaa-aaaoa-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"sgymv-uiaaa-aaaaa-aaaia-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rdmx6-jaaaa-aaaaa-aaadq-cai\"]")));

    }

    // Проверка статуса заявки в списке заявок по переданному id
    public void assertStatusInList(int id, String status) {
        // Свич на фрейм
        switchToFrameDocumentsList();
        String status_value = getText(STATUS_APPLICATION_IN_LIST_BY_ID.replace("{SUBSTRING}", Integer.toString(id)));
        Assert.assertEquals(status_value, status,
                "В списке заявок на валидацию у заявки с id: " + id + " получен статус: " + status_value +
                        " , а ожидалось: " + status);
    }

    // Проверка изменения значения в поле "Статус договора" (Accepted)
    public void assertAcceptedStatusContract(String value) {
        waitElementPresents(ACCEPTED_STATUS);
        String accepted_status = getText(ACCEPTED_STATUS);
        Assert.assertEquals(accepted_status, value,
                "Значение в поле 'Статус договора': " + accepted_status + " ожидалось: " + value);
    }

    // Проверка значения в поле "Статус договора"
    public void assertStatusContract(String value) {
        // Переход на фрейм
        switchToFrameDocumentsStatus();
        // Скролл до элемента
        scrollToElement(STATUS_CONTRACT_VALUE);
        // Получаем текст элемента
        String status_value = getText(STATUS_CONTRACT_VALUE);
        Assert.assertEquals(status_value, value,
                "Значение в поле 'Статус договора': " + status_value + " ожидалось: " + value);
    }

    // Проверка данных в полях формы заявки
    public void assertFormFields(DocumentData documentData) {
        // Свич на айфрейм
        switchToFrameApplicationForm();
        String lastName = getText(LASTNAME_VALUE);
        Assert.assertEquals(lastName, documentData.getLastName(),
                "В поле 'lastname' полученное значение: " + lastName + " ожидалось: " + documentData.getLastName());
        String firstName = getText(FIRSTNAME_VALUE);
        Assert.assertEquals(firstName, documentData.getFirstName(),
                "В поле 'firstName' полученное значение: " + firstName + " ожидалось: " + documentData.getFirstName());
        String middleName = getText(MIDDLENAME_VALUE);
        Assert.assertEquals(middleName, documentData.getMiddleName(),
                "В поле 'middleName' полученное значение: " + middleName + " ожидалось: " + documentData.getMiddleName());
        // TODO Переделать захардкоженое значение даты рождения
        String birth = getText(BIRTH_VALUE);
        Assert.assertEquals(birth, "************",
                "В поле 'birth' полученное значение: " + birth + " ожидалось: " + "************\n");
        String nationality = getText(NATIONALITY_VALUE);
        Assert.assertEquals(nationality, documentData.getNationalityValue(),
                "В поле 'nationality' полученное значение: " + nationality + " ожидалось: " + documentData.getNationalityValue());
    }

    // Клик на кнопку "Подтвердить"
    public void approveClick() {
        switchToFrameDocumentsStatus();
        waitElementPresents(APPROVE_BTN);
        click(APPROVE_BTN);
        waitDesapearElement(APPROVE_BTN);
    }
}
