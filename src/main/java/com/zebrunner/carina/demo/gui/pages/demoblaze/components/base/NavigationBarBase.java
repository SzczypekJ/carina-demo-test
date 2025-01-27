package com.zebrunner.carina.demo.gui.pages.demoblaze.components.base;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public abstract class NavigationBarBase extends AbstractUIObject {

    @FindBy(css = "#navbarExample > ul > li.nav-item.active > a")
    protected ExtendedWebElement homeButton;

    @FindBy(css ="#navbarExample > ul > li:nth-child(2) > a")
    protected ExtendedWebElement contactButton;

    @FindBy(css = "#navbarExample > ul > li:nth-child(3) > a")
    protected ExtendedWebElement aboutUsButton;

    @FindBy(xpath = ".//a[@id='cartur']")
    protected ExtendedWebElement cartButton;

    @FindBy(xpath = ".//a[@id='login2']")
    protected ExtendedWebElement loginButton;

    @FindBy(xpath = ".//a[@id='logout2']")
    protected ExtendedWebElement logoutButton;

    @FindBy(xpath = ".//a[@id='signin2']")
    protected ExtendedWebElement signInButton;

    public NavigationBarBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }


    public abstract void goToHome();

    public abstract void goToContact();

    public abstract void goToAboutUs();

    public abstract void goToCart();

    public abstract void goToLogin();

    public abstract void goToLogout();

    public abstract void goToSignIn();
}
