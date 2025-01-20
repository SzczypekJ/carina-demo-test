package com.zebrunner.carina.demo.gui.pages.common;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
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

    @FindBy(xpath = "//nav")
    protected NavigationBar navigationBar;

    @FindBy(id = "footc")
    protected Footer footer;

    public LoginPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract NavigationBar getNavigationBar();

    public abstract Footer getFooter();

    public abstract void login(String username, String password);

    public abstract String getWelcomeText();

    public abstract WebElement getWelcomeTextElement();

    public abstract ExtendedWebElement getUsernameField();
}
