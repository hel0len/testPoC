package model;

/* -------------------------------- Класс для объектов с данными полей Документов -------------------------------- */
public class DocumentData {
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final String nationalityValue;

    public DocumentData(String lastName, String firstName, String middleName, String nationalityValue) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.nationalityValue = nationalityValue;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public String getNationalityValue() {
        return nationalityValue;
    }
}
