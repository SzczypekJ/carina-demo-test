package com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

@Getter
public class NavigationBarDesktop extends NavigationBarBase {

    public NavigationBarDesktop(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    public void goToHome() {
        homeButton.click();
    }

    @Override
    public void goToContact() {
        contactButton.click();
    }

    @Override
    public void goToAboutUs() {
        aboutUsButton.click();
    }

    @Override
    public void goToCart() {
        cartButton.click();
    }

    @Override
    public void goToLogin() {
        loginButton.click();
    }

    @Override
    public void goToLogout() {
        logoutButton.click();
    }

    @Override
    public void goToSignIn() {
        signInButton.click();
    }
}
