package com.zebrunner.carina.demo.gui.pages.demoblaze;

import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class LoginPage extends AbstractPage {

    @FindBy(xpath = "//a[@id='login2']")
    private ExtendedWebElement loginButton;

    @FindBy(xpath = "//input[@id='loginusername']")
    private ExtendedWebElement usernameField;

    @FindBy(xpath = "//input[@id='loginpassword']")
    private ExtendedWebElement passwordField;

    @FindBy(xpath = "//a[@id='nameofuser']")
    private ExtendedWebElement welcomeText;

    @FindBy(xpath = "//button[@onclick='logIn()']")
    private ExtendedWebElement submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openLoginModal() {
        loginButton.click();
    }

    public void login(String username, String password) {
        usernameField.type(username);
        passwordField.type(password);
        submitButton.click();
    }

    public String getWelcomeText() {
        return welcomeText.getText();
    }

    public WebElement getWelcomeTextElement() {
        return welcomeText;
    }

    public void open() {
        driver.get(R.CONFIG.get("url"));
    }
}
