package com.zebrunner.carina.demo.gui.pages.demoblaze.android;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.LoginPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.FooterMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.NavigationBarMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.FooterDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.NavigationBarDesktop;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = LoginPageBase.class)
public class LoginPageAndroid extends LoginPageBase {

    @FindBy(xpath = "//nav")
    private NavigationBarMobile navigationBar;

    @FindBy(id = "footc")
    private FooterMobile footer;

    public LoginPageAndroid(WebDriver driver) {
        super(driver);
    }

    @Override
    public NavigationBarMobile getNavigationBar() {
        return navigationBar;
    }

    @Override
    public FooterMobile getFooter() {
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
