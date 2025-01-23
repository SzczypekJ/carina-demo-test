package com.zebrunner.carina.demo.gui.pages.demoblaze.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public class NavigationBar extends AbstractUIObject {

    @FindBy(css = "#navbarExample > ul > li.nav-item.active > a")
    private ExtendedWebElement homeButton;

    @FindBy(css ="#navbarExample > ul > li:nth-child(2) > a")
    private ExtendedWebElement contactButton;

    @FindBy(css = "#navbarExample > ul > li:nth-child(3) > a")
    private ExtendedWebElement aboutUsButton;

    @FindBy(xpath = ".//a[@id='cartur']")
    private ExtendedWebElement cartButton;

    @FindBy(xpath = ".//a[@id='login2']")
    private ExtendedWebElement loginButton;

    @FindBy(xpath = ".//a[@id='logout2']")
    private ExtendedWebElement logoutButton;

    @FindBy(xpath = ".//a[@id='signin2']")
    private ExtendedWebElement signInButton;


    public NavigationBar(WebDriver driver) {
        super(driver);
    }

    public NavigationBar(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void goToHome() {
        homeButton.click();
    }

    public void goToContact() {
        contactButton.click();
    }

    public void goToAboutUs() {
        aboutUsButton.click();
    }

    public void goToCart() {
        cartButton.click();
    }

    public void goToLogin() {
        loginButton.click();
    }

    public void goToLogout() {
        logoutButton.click();
    }

    public void goToSignIn() {
        signInButton.click();
    }
}
