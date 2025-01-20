package com.zebrunner.carina.demo.gui.pages.demoblaze;

import com.zebrunner.carina.demo.gui.pages.common.LoginPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = LoginPageBase.class)
public class LoginPage extends LoginPageBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public NavigationBar getNavigationBar() {
        return navigationBar;
    }

    @Override
    public Footer getFooter() {
        return footer;
    }

    @Override
    public void login(String username, String password) {
        usernameField.type(username);
        passwordField.type(password);
        submitButton.click();
    }

    @Override
    public String getWelcomeText() {
        return welcomeText.getText();
    }

    @Override
    public WebElement getWelcomeTextElement() {
        return welcomeText;
    }

    @Override
    public ExtendedWebElement getUsernameField() {
        return usernameField;
    }
}
