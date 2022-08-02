package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Account {

    protected static final SelenideElement firstNameInput = $("#FirstName");
    protected static final SelenideElement saveButton = $("[value='Save']");

    public Account openpage() {
        open("/customer/info");
        return this;
    }

    public Account changeUserFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    public Account saveChanges() {
        saveButton.click();
        return this;
    }

    public void checkFirstName(String firstName) {
        firstNameInput.shouldHave(attribute("value", firstName));
    }
}
