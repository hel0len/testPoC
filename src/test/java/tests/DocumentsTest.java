package tests;

import appmanager.App;
import model.DocumentData;
import org.testng.annotations.*;


/*-------------------------------------- Класс тестов на Документы пользователя  ------------------------------------*/
public class DocumentsTest extends App {

    // Создание и валидация документа
    @Test
    public void testCreateDocument() {
        // Тестовые данные
        DocumentData document = new DocumentData(
                "Иванов",
                "Иван",
                "Иванович",
                "Rus");

        // Авторизация под пользователем regular_1
        mainPage.authByUser(mainPage.REGULAR_1_IN_LIST, mainPage.PERSONAL_ACCOUNT);
        // Инициализируем создание документа
        documentPage.intiCreateDocument();
        // Заполняем форму шаблона
        documentPage.fillDocumentForm(document);
        // Клик на "Создать"
        documentPage.submitForm();
        // Получаем id созданной заявки
        int id = documentPage.getMaxId();
        // Открывам заявку
        documentPage.openDocumentById(id);
        // Проверка  полей на соответствие данным которые вводили при создании дока
        documentPage.assertFormFields(document);
        // Клик на кнопку "Отправить на валидацию"
        documentPage.goToValidation();
        // Проверка статуса заявки в поле "Статус договора"
        documentPage.assertStatusContract("Init");
        // Логаут
        mainPage.logout();
        // Авторизация под валидатором
        mainPage.authByUser(mainPage.VALIDATOR_IN_LIST, mainPage.VALIDATOR_ACCOUNT);
        // Открываем заявку
        validatorPage.openApplicationById(id);
        // Проверка статуса заявки в поле "Статус договора"
        validatorPage.assertStatusContract("Init");
        // Проверка что данные в полях заявки скрыты
        DocumentData f_document = new DocumentData("************", "************",
                "************", "************");
        validatorPage.assertFormFields(f_document);
        // Кликнуть на "Принять заявку"
        validatorPage.acceptClick();
        //Проверка статуса заявки в поле "Статус договора"
        validatorPage.assertAcceptedStatusContract("Accepted");
        // Логаут
        mainPage.logout();
        // Авторизация под пользователем regular_1
        mainPage.authByUser(mainPage.REGULAR_1_IN_LIST, mainPage.PERSONAL_ACCOUNT);
        // Открывам заявку
        documentPage.openDocumentById(id);
        // Клик на "Перейти к валлидации"
        documentPage.viewValidation();
        // Проверка обновления статуса заявки в поле "Статус договора"
        documentPage.assertUpdatedStatusContract("DataUpdated");
        // Логаут
        mainPage.logout();
        // Авторизация под валидатором
        mainPage.authByUser(mainPage.VALIDATOR_IN_LIST, mainPage.VALIDATOR_ACCOUNT);
        // Открываем заявку
        validatorPage.openApplicationById(id);
        // Проверка обновления статуса заявки в поле "Статус договора"
        validatorPage.assertStatusContract("DataUpdated");
        // Клик на кнопку "Подтвердить"
        validatorPage.approveClick();
        // Логаут
        mainPage.logout();
        // Авторизация под пользователем regular_1
        mainPage.authByUser(mainPage.REGULAR_1_IN_LIST, mainPage.PERSONAL_ACCOUNT);
        // Открывам заявку
        documentPage.openDocumentById(id);
        // Клик на "Перейти к валлидации"
        // documentPage.viewValidation();
        // Проверка значения в поле "Вердикт валидатора"
        documentPage.assertStatusApproveValidator("Данные корректны");
        // Клик на кнопку "Подтвердить"
        documentPage.approveClick();
        // Проверка значения в поле "Вердикт заявителя"
        documentPage.assertStatusApproveUser("Данные корректны");
        // Проверка статуса заявки в поле "Статус договора"
        documentPage.assertStatusContract("Approved");
        // Логаут
        mainPage.logout();
        // Авторизация под валидатором
        mainPage.authByUser(mainPage.VALIDATOR_IN_LIST, mainPage.VALIDATOR_ACCOUNT);
        // Открываем заявку
        validatorPage.openApplicationById(id);
        // Проверка статуса заявки в поле "Статус договора"
        validatorPage.assertStatusContract("Approved");
        // Логаут
        mainPage.logout();
        // Авторизация под пользователем regular_1
        mainPage.authByUser(mainPage.REGULAR_1_IN_LIST, mainPage.PERSONAL_ACCOUNT);
        documentPage.openDocumentById(id);
        // Клик на "Перейти к валлидации"
        documentPage.viewValidation();
        // Проверка статуса заявки в поле "Статус договора"
        // documentPage.assertVerifiedStatusContract("Verified");
        // Кликнуть на кнопку Завершить
        documentPage.completeClick();
        // Проверка статуса заявки в поле "Статус договора"
        documentPage.assertStatusContract("Completed");
    }
}
