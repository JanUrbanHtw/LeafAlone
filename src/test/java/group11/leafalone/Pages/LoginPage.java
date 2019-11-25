package group11.leafalone.Pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@PageUrl("http://localhost:8080/login")
public class LoginPage extends FluentPage {

    public static final String TITLE = "Login";
    private static final String LOGIN_FORM = "#loginForm";
    private static final String ERROR_MESSAGE = "#errorMessage";
    private static final String LOGOUT_MESSAGE = "#logoutMessage";

    @FindBy(id = "username")
    private FluentWebElement nameInput;

    @FindBy(id = "password")
    private FluentWebElement passwordInput;

    @FindBy(id = "submitButton")
    private FluentWebElement loginButton;

    public LoginPage inputName(String name) {
        nameInput.write(name);
        return this;
    }

    public LoginPage inputPassword(String password) {
        passwordInput.write(password);
        return this;
    }

    public LoginPage submitLoginForm() {
        loginButton.submit();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }

    public LoginPage assertErrorMessagePresent() {
        assertThat(el(ERROR_MESSAGE).present()).isTrue();
        return this;
    }

    public LoginPage assertErrorMessageNotPresent() {
        assertThat(el(ERROR_MESSAGE).present()).isFalse();
        return this;
    }

    public LoginPage assertLogoutMessagePresent() {
        assertThat(el(LOGOUT_MESSAGE).present()).isTrue();
        return this;
    }

    public LoginPage assertLogoutMessageNotPresent() {
        assertThat(el(LOGOUT_MESSAGE).present()).isFalse();
        return this;
    }

    public LoginPage assertTitleEquals(String title) {
        assertThat(window().title()).isEqualTo(title);
        return this;
    }

    public LoginPage clickOnElement(String element) {
        $(element).click();
        await().untilPage().isLoaded();
        return this;
    }

    private void awaitUntilLoginFormDisappear() {
        await().atMost(5, TimeUnit.SECONDS).until(el(LOGIN_FORM)).not().present();
    }
}
