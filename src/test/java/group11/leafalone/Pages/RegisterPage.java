package group11.leafalone.Pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

@PageUrl("http://localhost:8080/register")
public class RegisterPage extends FluentPage {

    public static final String TITLE = "Register";

    @FindBy(className = "errorMessage")
    private FluentWebElement errorMessage;

    @FindBy(id = "usernameInput")
    private FluentWebElement nameInput;

    @FindBy(id = "roleInput")
    private FluentWebElement roleInput;

    @FindBy(id = "emailInput")
    private FluentWebElement emailInput;

    @FindBy(id = "passwordInput")
    private FluentWebElement passwordInput;

    @FindBy(id = "confirmPasswordInput")
    private FluentWebElement confirmPasswordInput;

    @FindBy(id = "submitButton")
    private FluentWebElement submitButton;

    public RegisterPage inputUsername(String username) {
        nameInput.write(username);
        return this;
    }

    public RegisterPage setRole(String role) {
        roleInput.find("option", withText(role)).click();
        return this;
    }

    public RegisterPage inputEmail(String email) {
        emailInput.write(email);
        return this;
    }

    public RegisterPage inputPassword(String password) {
        passwordInput.write(password);
        return this;
    }

    public RegisterPage inputConfirmPassword(String password) {
        confirmPasswordInput.write(password);
        return this;
    }

    public RegisterPage submit() {
        submitButton.submit();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }

    public RegisterPage assertErrorMessagePresent() {
        assertThat(errorMessage.present()).isTrue();
        return this;
    }
}
