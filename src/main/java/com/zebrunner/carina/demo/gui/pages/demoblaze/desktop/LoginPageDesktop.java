package com.zebrunner.carina.demo.gui.pages.demoblaze.desktop;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.LoginPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.FooterDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.NavigationBarDesktop;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = LoginPageBase.class)
public class LoginPageDesktop extends LoginPageBase {

    @FindBy(xpath = "//nav")
    private NavigationBarDesktop navigationBar;

    @FindBy(id = "footc")
    private FooterDesktop footer;

    public LoginPageDesktop(WebDriver driver) {
        super(driver);
    }

    @Override
    public NavigationBarDesktop getNavigationBar() {
        return navigationBar;
    }

    @Override
    public FooterDesktop getFooter() {
        return footer;
    }
}
