package com.zebrunner.carina.demo.gui.pages.common.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.FooterBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public abstract class LoginPageBase extends AbstractPage {

    @FindBy(xpath = "//input[@id='loginusername']")
    protected ExtendedWebElement usernameField;

    @FindBy(xpath = "//input[@id='loginpassword']")
    protected ExtendedWebElement passwordField;

    @FindBy(xpath = "//a[@id='nameofuser']")
    protected ExtendedWebElement welcomeText;

    @FindBy(xpath = "//button[@onclick='logIn()']")
    protected ExtendedWebElement submitButton;


    public LoginPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract NavigationBarBase getNavigationBar();

    public abstract FooterBase getFooter();

    public abstract void login(String username, String password);

    public abstract String getWelcomeText();

    public abstract WebElement getWelcomeTextElement();

    public abstract ExtendedWebElement getUsernameField();
}
