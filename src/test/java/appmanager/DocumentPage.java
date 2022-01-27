package appmanager;

import model.DocumentData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/* ---------------------------- Методы работы с Документами в личном кабинете пользователя --------------------------*/
public class DocumentPage extends MainPage {

    /* ------------------------------------ Локаторы для страницы Документы ---------------------------------------- */
    public final String
            DOCUMENT_IN_LIST_BY_ID = "//li[@id=\"{SUBSTRING}\"]",
            ADD_DOCUMENT_BTN = "//button[@id=\"add-document\"]/span/span",
            MHRus = "//div[contains(text(), \"MHRus\")]",
            PASSPORT_A = "//li/div[contains(text(), \"Паспорт А\")]",
            LASTNAME_FIELD = "//input[@id=\"lastname\"]",
            FIRSTNAME_FIELD = "//input[@id=\"firstname\"]",
            MIDDLENAME_FIELD = "//input[@id=\"middlename\"]",
            NATIONALITY_FIELD = "//input[@id=\"nationality\"]",
            FILE_FIELD = "//input[@type=\"file\"]",
            SUBMIT_FORM = "//button[@id=\"submit\"]",
            BIRTH_FIELD = "//input[@id=\"birth\"]/../div",
            LASTNAME_VALUE = "//div[@id=\"lastname\"]/div[2]/span",
            FIRSTNAME_VALUE = "//div[@id=\"firstname\"]/div[2]/span",
            MIDDLENAME_VALUE = "//div[@id=\"middlename\"]/div[2]/span",
            BIRTH_VALUE = "//div[@id=\"birth\"]/div[2]/span",
            NATIONALITY_VALUE = "//div[@id=\"nationality\"]/div[2]/span",
            STATUS_CONTRACT_VALUE = "//span[contains(text(), \"Статус договора\")]/../following-sibling::div/span",
            DATA_UPDATED_STATUS = "//span[contains(text(), \"Статус договора\")]/../following-sibling::div/span[contains(text(), \"DataUpdated\")]",
            VERIFIED_STATUS = "//span[contains(text(), \"Статус договора\")]/../following-sibling::div/span[contains(text(), \"Verified\")]",
            STATUS_APPROVE_VALIDATOR = "//span[contains(text(), \"Вердикт валидатора\")]/../following-sibling::div/span",
            STATUS_APPROVE_USER = "//span[contains(text(), \"Вердикт заявителя\")]/../following-sibling::div/span",
            SEND_TO_VALIDATION = "//button[@id=\"send-to-validation\"]",
            VIEW_VALIDATION = "//button[@id=\"view-validation\"]",
            APPROVE_BTN = "//button[@id=\"approve\"]",
            COMPLETE_BTN = "//button[@id=\"complete\"]",

    //TODO Переделать захардкоженую дату в локаторе
    DATE_BIRTH_IN_CALENDAR = "//abbr[@aria-label=\"17 января 2022 г.\"]";


    public DocumentPage(WebDriver driver) {
        super(driver);
    }

    // Кликнуть на "Отправить на валидацию"
    public void goToValidation() {
        switchToFrameWithValidationButton();
        click(SEND_TO_VALIDATION);
        waitDesapearElement(SEND_TO_VALIDATION);
    }

    // Переходим на фрейм на котором кнопка Отправить на валидацию
    public void switchToFrameWithValidationButton() {
        // Свич на айфрейм
        driver.switchTo().defaultContent();
        //Переходим на фрейм документов
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"sgymv-uiaaa-aaaaa-aaaia-cai\"]")));
    }

    // Проверки полей в форме на соответствие данным которые вводили при создании дока
    public void assertFormFields(DocumentData documentData) {
        // Свич на айфрейм
        switchToFrameFormFields();
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
        Assert.assertEquals(birth, "17-01-2022",
                "В поле 'birth' полученное значение: " + birth + " ожидалось: " + "17-01-2022");
        String nationality = getText(NATIONALITY_VALUE);
        Assert.assertEquals(nationality, documentData.getNationalityValue(),
                "В поле 'nationality' полученное значение: " + nationality + " ожидалось: " + documentData.getNationalityValue());
    }

    // Переход на фрейм с в полями заявки
    public void switchToFrameFormFields() {
        // Свич на айфрейм
        driver.switchTo().defaultContent();
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rdmx6-jaaaa-aaaaa-aaadq-cai\"]")));
    }

    // Открывам заявку по переданному id
    public void openDocumentById(int max_id) {
        switchToFrameDocuments();
        String locator = DOCUMENT_IN_LIST_BY_ID.replace("{SUBSTRING}", Integer.toString(max_id));
        click(locator);
    }

    // Получение локатора последней созданной заявки
    public String getLocatorBySubstring(int max_id) {
        String locator = "//li[@id=\"{SUBSTRING}\"]".replace("{SUBSTRING}", Integer.toString(max_id));
        return locator;
    }

    // Возвращает максимальный id из списка заявок
    public int getMaxId() {
        // Свич на айфрейм
        switchToFrameDocuments();
        List<WebElement> docs = driver.findElements(By.xpath("//button/../div/li"));
        int max_id = 0;
        for (WebElement i : docs) {
            int id = Integer.parseInt(i.getAttribute("id"));
            if (id > max_id) {
                max_id = id;
            }
        }
        return max_id;
    }

    // Заполняем форму шаблона Документа
    public void fillDocumentForm(DocumentData documentData) {
        // Свич на айфрейм
        switchToFrameDocumentForm();
        type(LASTNAME_FIELD, documentData.getLastName());
        type(FIRSTNAME_FIELD, documentData.getFirstName());
        type(MIDDLENAME_FIELD, documentData.getMiddleName());

        // TODO В поле даты нельзя копировать значение напрямую, только через открытие календаря
        //  Переделать локатор при взаимодействии с календарем
        click(BIRTH_FIELD);
        click(DATE_BIRTH_IN_CALENDAR);
        type(NATIONALITY_FIELD, documentData.getNationalityValue());
        type(FILE_FIELD, "/Users/lenachagina/Projects/TestPoC/src/test/resources/photo_test.jpeg");
    }

    // Клик на кнопку "Создать" внизу формы документы (без проверки на доступность)
    public void submitForm() {
        click(SUBMIT_FORM);
        waitDesapearElement(SUBMIT_FORM);
    }

    // Переходим на фрейм с формой
    public void switchToFrameDocumentForm() {
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id=\"rdmx6-jaaaa-aaaaa-aaadq-cai\"]")));
    }

    // Инициализируем создание документа
    public void intiCreateDocument() {
        switchToFrameDocuments();
        click(ADD_DOCUMENT_BTN);
        click(MHRus);
        click(PASSPORT_A);
    }

    // Переходим на фрейм документов
    public void switchToFrameDocuments() {
        driver.switchTo().defaultContent();
        driver
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"rkp4c-7iaaa-aaaaa-aaaca-cai\"]")))
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id=\"tlwi3-3aaaa-aaaaa-aaapq-cai\"]")));
    }

    // Проверка значения в поле "Статус договора"
    public void assertStatusContract(String value) {
        // Переход на фрейм
        switchToFrameWithValidationButton();
        // Ожидание элемента
        waitElementPresents(STATUS_CONTRACT_VALUE);
        String status_value = getText(STATUS_CONTRACT_VALUE);
        Assert.assertEquals(status_value, value,
                "Значение в поле 'Статус договора': " + status_value + " ожидалось: " + value);

    }

    // Проверка изменения значения в поле "Статус договора"
    public void assertUpdatedStatusContract(String value) {
        // Переход на фрейм
        switchToFrameWithValidationButton();
        // Ожидание присутствия элемента
        waitElementPresents(DATA_UPDATED_STATUS);
        String accepted_status = getText(DATA_UPDATED_STATUS);
        Assert.assertEquals(accepted_status, value,
                "Значение в поле 'Статус договора': " + accepted_status + " ожидалось: " + value);
    }

    // Проверка изменения значения в поле "Статус договора"
    public void assertVerifiedStatusContract(String value) {
        // Переход на фрейм
        switchToFrameWithValidationButton();
        // Ожидание присутствия элемента
        waitElementPresents(VERIFIED_STATUS);
        String accepted_status = getText(VERIFIED_STATUS);
        Assert.assertEquals(accepted_status, value,
                "Значение в поле 'Статус договора': " + accepted_status + " ожидалось: " + value);
    }

    // Кликнуть на кнопку "Перейти к валидации"
    public void viewValidation() {
        switchToFrameWithValidationButton();
        click(VIEW_VALIDATION);
        waitDesapearElement(VIEW_VALIDATION);
    }

    // Проверка значения в поле "Вердикт валидатора"
    public void assertStatusApproveValidator(String value) {
        // Переход на фрейм
        switchToFrameWithValidationButton();
        String status_value = getText(STATUS_APPROVE_VALIDATOR);
        Assert.assertEquals(status_value, value,
                "Значение в поле 'Вердикт валидатора': " + status_value + " ожидалось: " + value);
    }

    // Кликнуть на кнопку "Подтвердить"
    public void approveClick() {
        switchToFrameWithValidationButton();
        waitElementPresents(APPROVE_BTN);
        click(APPROVE_BTN);
        waitDesapearElement(APPROVE_BTN);
    }

    // Проверка значения в поле "Вердикт заявителя"
    public void assertStatusApproveUser(String value) {
        // Переход на фрейм
        switchToFrameWithValidationButton();
        String status_value = getText(STATUS_APPROVE_USER);
        Assert.assertEquals(status_value, value,
                "Значение в поле 'Вердикт заявителя': " + status_value + " ожидалось: " + value);
    }

    public void completeClick() {
        switchToFrameWithValidationButton();
        click(COMPLETE_BTN);
        waitDesapearElement(COMPLETE_BTN);
    }
}

